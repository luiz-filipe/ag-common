# AG Common

A framework implemented in Java that allows you to loosely describe environments, agents 
and tasks. This gives flexibility and allows different entities to be reused. It can 
easily be used to describe BDI agents for example. This is framework is part of my final 
MSc project that can be [downloaded here](http://luizfilipe.com/ag/msc-luiz-filipe.pdf).

## Two-minute introduction
All you need to know to understand the following is:

* An environment is a 2 dimensional space made up of instances of the `Node` interface
* Agents are assigned a type and can be placed in virtually any node of an environment
* Tasks are assigned to agent types


### Defining a task
```Java
@Immutable
public class WandererTask extends AbstractTask {
    public static final String NAME = "Wanderer";

    public WandererTask() {
        super(WandererTask.NAME);
    }

    /**
    * Randomly navigates from node to node in the environment.
    * Note that this method would fall into a deadlock if the agent's current node is not
    * connected to any other node.
    */
    @Override
    public void execute(final Agent agent) {
        final Node neighbourNode = agent.getCurrentNode()
                .getNeighbour(Direction.getRandomDirection());

        if (neighbourNode != null) {
            neighbourNode.addAgent(agent);
        } else {
            this.execute(agent);
        }
    }
}
```

### Defining an agent type and an agent

```Java
@ThreadSafe
public enum BasicTaskAgentType implements TaskAgentType {
    TYPE;

    private final String name = "agent:type:basic";
    private final List<Task> tasks;

    private BasicTaskAgentType() {
        tasks = new ArrayList<Task>();
        tasks.add(new WandererTask());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
}
```

```Java
@ThreadSafe
public class BasicAgent extends TaskAgent {
    public BasicAgent(String id) {
        super(id, BasicTaskAgentType.TYPE, false);
    }

    @Override
    public Void call() throws Exception {
        while (!Thread.currentThread().isInterrupted()) {
            this.getTaskByName(WandererTask.NAME).execute(this);

            try {
                Thread.sleep(5);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }
}
```

### Running a simulation

```Java
public class BasicSimulation {
    public static void main(String[] args) {
        final Environment env = new BasicEnvironment(200, 200);
        final Simulation sim = new Simulation("target/", env, 20);
        final Agent a = new BasicAgent("agent");

        sim.addAgentMiddleEnvironment(a);

        sim.scheduleEnvironmentExploredRenderer("explored-5.png", 5, TimeUnit.SECONDS);
        sim.scheduleEnvironmentExploredRenderer("explored-10.png", 10, TimeUnit.SECONDS);
        sim.scheduleEnvironmentExploredRenderer("explored-20.png", 20, TimeUnit.SECONDS);
        sim.scheduleEnvironmentExploredRenderer("explored-30.png", 30, TimeUnit.SECONDS);

        sim.run(30, TimeUnit.SECONDS);
    }
```
In the last section we used the simulation to schedule 5 `ExploredEnvironmentRenderer` at
5, 10, 20 and 30 seconds respectively. This kind of renderer in particular creates a
snapshot of the environment, marking a node that has been visited by any agent in black.

### Analysing the simulation output

The result:

![alt tag](http://luizfilipe.com/ag/two-minute-results.jpg)


## Overview

We can divide the framework in two main areas, the model and the utility classes.

### Model 

The three core components of the model are the packages:
 
1. Environment 
2. Agent
3. Tasks

#### Environment

##### Node

The fundamental entity to represent the environment is the 
[`Node`](src/main/java/org/ag/common/env/Node.java) interface. A node can be seen as an
infinitesimal piece of the environment. By linking nodes together it is possible to create
complex network of objects that will describe the space where agents can navigate. 

See: [`Node`](src/main/java/org/ag/common/env/Node.java), 
[`BasicNode`](src/main/java/org/ag/common/env/BasicNode.java)

##### Communication Stimulus

Agents can shape the environment they belong to in various ways, communication stimuli 
are one. They are deposited by the agents onto the environment. Other agents can read 
these stimuli and use them to draw any conclusions and take decisions based on the state
of the environment.

The [`CommunicationStimulus`](src/main/java/org/ag/common/env/CommunicationStimulus.java) 
interface is an abstraction of any communication inter-action.

See: [`CommunicationStimulus`](src/main/java/org/ag/common/env/CommunicationStimulus.java), 
[`BasicCommunicationStimulus`](src/main/java/org/ag/common/env/BasicCommunicationStimulus.java)


##### Communication Stimulus Type

We have many ways to communicate, we can talk to other people, we can write them an e-mail
or use sign language to transmit any information we find useful to transmit.
 
The [`CommunicationStimulusType`](src/main/java/org/ag/common/env/CommunicationStimulusType.java)
interface is an high level abstraction of a specific way to communicate.

See: [`CommunicationStimulusType`](src/main/java/org/ag/common/env/CommunicationStimulusType.java)

##### Environment Element

An environment element is an abstraction to anything that could be added to the 
environment grid. For example, nodes that could represent obstacles in the element. 
Each element has a dimension, a colour that is used by the renderer, and identification 
string that should be unique for each environment.

See: [`EnvironmentElement`](src/main/java/org/ag/common/env/EnvironmentElement.java), 
[`BasicEnvironmentElement`](src/main/java/org/ag/common/env/BasicEnvironmentElement.java)

##### Other

###### Coordinate

Representation of a coordinate in the environment.

See: [`Coordinate`](src/main/java/org/ag/common/env/Coordinate.java)

###### Direction

Nodes are connected in a 8-way, that is, they can have 8 different neighbours. This class
declares these directions. They are used throughout the framework in order to select
nodes, move agents and create environments.

See: [`Direction`](src/main/java/org/ag/common/env/Direction.java)

![alt tag](http://luizfilipe.com/ag/overview-env.jpg)

#### Agent

##### Agent Interface

The abstraction of any agent is the [`Agent`](src/main/java/org/ag/common/agent/Agent.java)
interface. It formalises the basic Application Public Interface (API) that is available 
to any agent in the model.

See: [`Agent`](src/main/java/org/ag/common/agent/Agent.java),
[`AbstractAgent`](src/main/java/org/ag/common/agent/AbstractAgent.java),
[`TaskAgent`](src/main/java/org/ag/common/agent/TaskAgent.java)

##### Agent Type

Agents belong to a certain type, the [`AgentType`](src/main/java/org/ag/common/agent/AgentType.java)
interface formalises that. Classes that implement this interface are associated to agents
and aggregate behaviour that will govern how the agents of these types will present.

See: [`AgentType`](src/main/java/org/ag/common/agent/AgentType.java),
[`TaskAgentType`](src/main/java/org/ag/common/agent/TaskAgentType.java),
[`BasicTaskAgentType`](src/main/java/org/ag/common/agent/BasicTaskAgentType.java)

![alt tag](http://luizfilipe.com/ag/overview-ag.jpg)

#### Task

##### Task Interface

![alt tag](http://luizfilipe.com/ag/overview-task.jpg)
