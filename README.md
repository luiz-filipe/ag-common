# AG Common

A framework implemented in Java that allows you to loosely describe environments, agents and tasks. This gives 
flexibility and allows different entities to be reused. It can easily be used to describe BDI agents for example.

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
    * Note that this method would fall into a deadlock if the agent's current node is not connected 
    * to any other node.
    */
    @Override
    public void execute(final Agent agent) {
        final Node neighbourNode = agent.getCurrentNode().getNeighbour(Direction.getRandomDirection());

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
In the last section we used the simulation to schedule 5 `ExploredEnvironmentRenderer` at 5, 10, 20
and 30 seconds respectively. This kind of renderer in particular creates a snapshot of the
environment, marking a node that has been visited by any agent in black.

### Analysing the simulation output

The result:

![alt tag](http://luizfilipe.com/ag/two-minute-results.jpg)

The images describe the nodes that the agent had visited at the point in time the enviroment snapshots were taken (5, 10, 20, 30 seconds in the simulation). As you can see the last image is effectivelly the union of the other ones.
