/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package sink.scene3d;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;


public class Group3d extends Actor3d{
	private final SnapshotArray<Actor3d> children = new SnapshotArray<Actor3d>(true, 4, Actor3d.class);
	
	public Group3d(){
		super();
	}
	
	public Group3d(Model model){
		super(model);
	}
	
	public void act (float delta) {
        super.act(delta);
        Actor3d[] actors = children.begin();
        for (int i = 0, n = children.size; i < n; i++)
                actors[i].act(delta);
        children.end();
	}
	
	public void drawChildren(ModelBatch modelBatch, Environment environment){
		 modelBatch.render(this, environment);
	     modelBatch.render(children, environment);
	     for(Actor3d a: children){
	    	 if(a instanceof Group3d){
	    		 ((Group3d) a).drawChildren(modelBatch, environment);
	    	 }
	     }
	}
	 
    /** Adds an actor as a child of this group. The actor is first removed from its parent group, if any.
     * @see #remove() */
    public void addActor(Actor3d actor) {
         actor.remove();
         children.add(actor);
         actor.setParent(this);
         actor.setStage3d(getStage3d());
         childrenChanged();
    }
    
    /** Removes an actor from this group. If the actor will not be used again and has actions, they should be
     * {@link Actor#clearActions3d() cleared} so the actions will be returned to their
     * {@link Action#setPool(com.badlogic.gdx.utils.Pool) pool}, if any. This is not done automatically. */
    public boolean removeActor(Actor3d actor) {
            if (!children.removeValue(actor, true)) return false;
            Stage3d stage = getStage3d();
            if (stage != null) stage.unfocus(actor);
            actor.setParent(null);
            actor.setStage3d(null);
            childrenChanged();
            return true;
    }
    
    @Override
    public void addAction3d (Action3d action){
    	super.addAction3d(action);
    	Actor3d[] actors = children.begin();
        for (int i = 0, n = children.size; i < n; i++)
                actors[i].addAction3d(action);
        children.end();
    }

    @Override
	public void removeAction3d (Action3d action) {
		super.removeAction3d(action);
		Actor3d[] actors = children.begin();
        for (int i = 0, n = children.size; i < n; i++)
                actors[i].removeAction3d(action);
        children.end();
	}
    
    /** Called when actors are added to or removed from the group. */
    protected void childrenChanged () {
    }
    
    /** Removes all actors from this group. */
    public void clearChildren () {
            Actor3d[] actors = children.begin();
            for (int i = 0, n = children.size; i < n; i++) {
                    Actor3d child = actors[i];
                    child.setStage3d(null);
                    child.setParent(null);
            }
            children.end();
            children.clear();
            childrenChanged();
    }

    /** Removes all children, actions, and listeners from this group. */
    public void clear () {
            super.clear();
            clearChildren();
    }

    /** Returns the first actor found with the specified name. Note this recursively compares the name of every actor in the group. */
    public Actor3d findActor (String name) {
            Array<Actor3d> children = this.children;
            for (int i = 0, n = children.size; i < n; i++)
                    if (name.equals(children.get(i).getName())) return children.get(i);
            for (int i = 0, n = children.size; i < n; i++) {
                    Actor3d child = children.get(i);
                    if (child instanceof Group3d) {
                            Actor3d actor = ((Group3d)child).findActor(name);
                            if (actor != null) return actor;
                    }
            }
            return null;
    }
    
    @Override
    protected void setStage3d (Stage3d stage3d) {
        super.setStage3d(stage3d);
        Array<Actor3d> children = this.children;
        for (int i = 0, n = children.size; i < n; i++)
                children.get(i).setStage3d(stage3d);
    }
    
    /** Returns an ordered list of child actors in this group. */
    public SnapshotArray<Actor3d> getChildren () {
    	return children;
    }
    
    public boolean hasChildren () {
    	return children.size > 0;
    }
    
    /** Prints the actor hierarchy recursively for debugging purposes. */
    public void print () {
            print("");
    }

    private void print (String indent) {
            Actor3d[] actors = children.begin();
            for (int i = 0, n = children.size; i < n; i++) {
                    System.out.println(indent + actors[i]);
                    if (actors[i] instanceof Group3d) ((Group3d)actors[i]).print(indent + "|  ");
            }
            children.end();
    }

}
