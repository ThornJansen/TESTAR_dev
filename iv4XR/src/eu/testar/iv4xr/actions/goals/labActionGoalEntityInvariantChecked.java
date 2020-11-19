/***************************************************************************************************
 *
 * Copyright (c) 2020 Universitat Politecnica de Valencia - www.upv.es
 * Copyright (c) 2020 Open Universiteit - www.ou.nl
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************************************/

package eu.testar.iv4xr.actions.goals;

import java.util.function.Predicate;

import org.fruit.alayer.Role;
import org.fruit.alayer.SUT;
import org.fruit.alayer.State;
import org.fruit.alayer.Tags;
import org.fruit.alayer.exceptions.ActionFailedException;

import eu.iv4xr.framework.world.WorldEntity;
import eu.testar.iv4xr.LabRecruitsAgentTESTAR;
import eu.testar.iv4xr.actions.iv4xrActionRoles;
import eu.testar.iv4xr.enums.IV4XRtags;
import nl.uu.cs.aplib.mainConcepts.GoalStructure;

public class labActionGoalEntityInvariantChecked extends labActionGoal {

	private static final long serialVersionUID = 7543700026350051066L;

	private String agentId;
	private String entityId;
	private String info;
	private Predicate<WorldEntity> predicate;
	private LabRecruitsAgentTESTAR agentTESTAR;
	private GoalStructure goalStructure;

	public labActionGoalEntityInvariantChecked(State state, LabRecruitsAgentTESTAR testAgent, GoalStructure goalStructure, String agentId, String entityId, String info, Predicate<WorldEntity> predicate) {
		this.set(Tags.Role, iv4xrActionRoles.iv4xrHighActionGoalEntityInvariantChecked);
		this.set(Tags.OriginWidget, state);
		this.agentTESTAR = testAgent;
		this.goalStructure = goalStructure;
		this.agentId = agentId;
		this.entityId = entityId;
		this.info = info;
		this.predicate = predicate;
		this.set(Tags.Desc, toShortString());
		this.set(IV4XRtags.agentAction, false);
		this.set(IV4XRtags.newActionByAgent, false);
		
		// Set the goal to the agent
		agentTESTAR.setGoal(goalStructure);
	}
	
	@Override
	public GoalStructure getActionGoal() {
		return goalStructure;
	}

	@Override
	public void run(SUT system, State state, double duration) throws ActionFailedException {
		// It has been decided to execute this action
		// Send the instructions to achieve the goal
		agentTESTAR.update();
	}

	@Override
	public String toShortString() {
		return "Agent: " + agentId + " executing Goal EntityInvariantChecked to " + entityId
				+ " with info " + info + " of predicate " + predicate;
	}

	@Override
	public String toParametersString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(Role... discardParameters) {
		// TODO Auto-generated method stub
		return null;
	}

}