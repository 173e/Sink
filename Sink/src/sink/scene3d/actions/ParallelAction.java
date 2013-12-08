package sink.scene3d.actions;

import sink.scene3d.Action3d;
import sink.scene3d.Actor3d;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;



/** Executes a number of actions at the same time.
 * @author Nathan Sweet */
public class ParallelAction extends Action3d {
        Array<Action3d> actions = new Array(4);
        private boolean complete;

        public ParallelAction () {
        }

        public ParallelAction (Action3d action1) {
                addAction(action1);
        }

        public ParallelAction (Action3d action1, Action3d action2) {
                addAction(action1);
                addAction(action2);
        }

        public ParallelAction (Action3d action1, Action3d action2, Action3d action3) {
                addAction(action1);
                addAction(action2);
                addAction(action3);
        }

        public ParallelAction (Action3d action1, Action3d action2, Action3d action3, Action3d action4) {
                addAction(action1);
                addAction(action2);
                addAction(action3);
                addAction(action4);
        }

        public ParallelAction (Action3d action1, Action3d action2, Action3d action3, Action3d action4, Action3d action5) {
                addAction(action1);
                addAction(action2);
                addAction(action3);
                addAction(action4);
                addAction(action5);
        }

        public boolean act (float delta) {
                if (complete) return true;
                complete = true;
                Pool pool = getPool();
                setPool(null); // Ensure this action can't be returned to the pool while executing.
                try {
                        Array<Action3d> actions = this.actions;
                        for (int i = 0, n = actions.size; i < n && actor3d != null; i++) {
                                if (!actions.get(i).act(delta)) complete = false;
                                if (actor3d == null) return true; // This action was removed.
                        }
                        return complete;
                } finally {
                        setPool(pool);
                }
        }

        public void restart () {
                complete = false;
                Array<Action3d> actions = this.actions;
                for (int i = 0, n = actions.size; i < n; i++)
                        actions.get(i).restart();
        }

        public void reset () {
                super.reset();
                actions.clear();
        }

        public void addAction (Action3d action) {
                actions.add(action);
                if (actor3d != null) action.setActor3d(actor3d);
        }

        public void setActor (Actor3d actor) {
                Array<Action3d> actions = this.actions;
                for (int i = 0, n = actions.size; i < n; i++)
                        actions.get(i).setActor3d(actor);
                super.setActor3d(actor);
        }

        public Array<Action3d> getActions () {
                return actions;
        }

        public String toString () {
                StringBuilder buffer = new StringBuilder(64);
                buffer.append(super.toString());
                buffer.append('(');
                Array<Action3d> actions = this.actions;
                for (int i = 0, n = actions.size; i < n; i++) {
                        if (i > 0) buffer.append(", ");
                        buffer.append(actions.get(i));
                }
                buffer.append(')');
                return buffer.toString();
        }
}