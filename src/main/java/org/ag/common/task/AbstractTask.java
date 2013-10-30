package org.ag.common.task;

import net.jcip.annotations.Immutable;

/**
 * Base implementation of Task interface.
 *
 * <p>This class is immutable, and any other classes that extend this one should also be.</p>
 *
 * <p>Tasks are identified by name. They should be unique strings. Another important point is that tasks should be
 * stateless, as the same object instance of a task will be shared by many agents.</p>
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@Immutable
public abstract class AbstractTask implements Task {
    protected final String name;

    /**
     * Constructs a task with an unique identifier.
     *
     * @param id unique identifier.
     */
    public AbstractTask(final String id) {
        this.name = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof AbstractTask)) {
            return false;
        }

        AbstractTask other = (AbstractTask) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}