
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


import org.fruit.alayer.Action;
import org.fruit.alayer.SUT;
import org.fruit.alayer.State;
import org.fruit.alayer.Verdict;
import org.fruit.alayer.exceptions.ActionBuildException;
import org.fruit.alayer.exceptions.StateBuildException;
import org.fruit.alayer.exceptions.SystemStartException;
import org.fruit.monkey.Settings;
import org.testar.protocols.DesktopProtocol;

import java.util.Set;

import org.fruit.alayer.windows.WinProcess;
import org.testar.jacoco.JacocoReportReader;
import org.testar.jacoco.MBeanClient;
import org.testar.OutputStructure;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import com.google.common.io.Files;


import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.fruit.Util;
import org.fruit.alayer.Action;
import org.fruit.alayer.exceptions.ActionBuildException;
import org.fruit.alayer.windows.WinProcess;
import org.fruit.monkey.ConfigTags;
import org.fruit.monkey.Settings;
import org.fruit.alayer.SUT;
import org.fruit.alayer.State;
import org.fruit.alayer.Widget;
import org.fruit.alayer.actions.AnnotatingActionCompiler;
import org.fruit.alayer.actions.StdActionCompiler;
import org.fruit.alayer.devices.AWTKeyboard;
import org.fruit.alayer.devices.KBKeys;
import org.fruit.alayer.devices.Keyboard;
import org.fruit.alayer.Tags;
import org.testar.OutputStructure;
import org.testar.jacoco.JacocoReportReader;
import org.testar.jacoco.MBeanClient;


import com.google.common.io.Files;

import static org.fruit.alayer.Tags.Blocked;
import static org.fruit.alayer.Tags.Enabled;

import java.io.FileWriter;
import java.io.IOException;

import org.fruit.alayer.*;



/**
 * This protocol provides default TESTAR behaviour to test Windows desktop applications.
 *
 * It uses random action selection algorithm.
 */
public class Protocol_desktop_generic_pure_random_action extends DesktopProtocol {
	
	private long startSequenceTime;
	private String reportTimeDir;

	/**
	 * Called once during the life time of TESTAR
	 * This method can be used to perform initial setup work
	 * @param   settings  the current TESTAR settings as specified by the user.
	 */
	@Override
	protected void initialize(Settings settings){
		super.initialize(settings);
		
		// TESTAR will execute the SUT with Java
		// We need this to add JMX parameters properly (-Dcom.sun.management.jmxremote.port=5000)
		//WinProcess.java_execution = true;
	}

	/**
	 * This methods is called before each test sequence, before startSystem(),
	 * allowing for example using external profiling software on the SUT
	 *
	 * HTML sequence report will be initialized in the super.preSequencePreparations() for each sequence
	 */
	@Override
	protected void preSequencePreparations() {
		super.preSequencePreparations();
	}

	/**
	 * This method is called when TESTAR starts the System Under Test (SUT). The method should
	 * take care of
	 *   1) starting the SUT (you can use TESTAR's settings obtainable from <code>settings()</code> to find
	 *      out what executable to run)
	 *   2) waiting until the system is fully loaded and ready to be tested (with large systems, you might have to wait several
	 *      seconds until they have finished loading)
	 * @return  a started SUT, ready to be tested.
	 */
	@Override
	protected SUT startSystem() throws SystemStartException{
		return super.startSystem();
	}

	/**
	 * This method is invoked each time the TESTAR starts the SUT to generate a new sequence.
	 * This can be used for example for bypassing a login screen by filling the username and password
	 * or bringing the system into a specific start state which is identical on each start (e.g. one has to delete or restore
	 * the SUT's configuration files etc.)
	 */
	 @Override
	protected void beginSequence(SUT system, State state){
		startSequenceTime = System.currentTimeMillis();
		try{
			reportTimeDir = new File(OutputStructure.outerLoopOutputDir).getCanonicalPath();
			//myWriter = new FileWriter(reportTimeDir + "/_sequenceTimeUntilActions.txt");
		} catch (Exception e) {
				System.out.println("sequenceTimeUntilActions.txt can not be created " );
				e.printStackTrace();
		}
	 	super.beginSequence(system, state);
	}

	/**
	 * This method is called when the TESTAR requests the state of the SUT.
	 * Here you can add additional information to the SUT's state or write your
	 * own state fetching routine. The state should have attached an oracle
	 * (TagName: <code>Tags.OracleVerdict</code>) which describes whether the
	 * state is erroneous and if so why.
	 *
	 * super.getState(system) puts the state information also to the HTML sequence report
	 *
	 * @return  the current state of the SUT with attached oracle.
	 */
	@Override
	protected State getState(SUT system) throws StateBuildException{
		return super.getState(system);
	}

	/**
	 * The getVerdict methods implements the online state oracles that
	 * examine the SUT's current state and returns an oracle verdict.
	 * @return oracle verdict, which determines whether the state is erroneous and why.
	 */
	@Override
	protected Verdict getVerdict(State state){
		// The super methods implements the implicit online state oracles for:
		// system crashes
		// non-responsiveness
		// suspicious titles
		Verdict verdict = super.getVerdict(state);

		//--------------------------------------------------------
		// MORE SOPHISTICATED STATE ORACLES CAN BE PROGRAMMED HERE
		//--------------------------------------------------------

		return verdict;
	}

	/**
	 * This method is used by TESTAR to determine the set of currently available actions.
	 * You can use the SUT's current state, analyze the widgets and their properties to create
	 * a set of sensible actions, such as: "Click every Button which is enabled" etc.
	 * The return value is supposed to be non-null. If the returned set is empty, TESTAR
	 * will stop generation of the current action and continue with the next one.
	 * @param system the SUT
	 * @param state the SUT's current state
	 * @return  a set of actions
	 */
	@Override
	protected Set<Action> deriveActions(SUT system, State state) throws ActionBuildException{

		//The super method returns a ONLY actions for killing unwanted processes if needed, or bringing the SUT to
		//the foreground. You should add all other actions here yourself.
		// These "special" actions are prioritized over the normal GUI actions in selectAction() / preSelectAction().
		Set<Action> actions = super.deriveActions(system,state);

		// Derive left-click actions, click and type actions, and scroll actions from
		// all widgets of the GUI:
		actions = deriveClickTypeScrollActionsFromAllWidgetsOfState(actions, system, state);
		
		
		
		//-----------------------------------------------------------------------------------------------------------BEGIN FROM DESKTOP_GENERIC Z
		// To derive actions (such as clicks, drag&drop, typing ...) we should first create an action compiler.
        StdActionCompiler ac = new AnnotatingActionCompiler();

        // To find all possible actions that TESTAR can click on we should iterate through all widgets of the state.
        for(Widget w : state){
            //optional: iterate through top level widgets based on Z-index:
            //for(Widget w : getTopWidgets(state)){

            if(w.get(Tags.Role, Roles.Widget).toString().equalsIgnoreCase("UIAMenu")){
                // filtering out actions on menu-containers (that would add an action in the middle of the menu)
                continue; // skip this iteration of the for-loop
            }
            
			// BLUEJ
            // Residual and strange widgets to ignore the click action
            if(w.get(Tags.Desc,"").equals("decrement") || w.get(Tags.Desc,"").equals("increment")){
                continue;
            }

            // Only consider enabled and non-blocked widgets
            if(w.get(Enabled, true) && !w.get(Blocked, false)){

                // Do not build actions for widgets on the blacklist
                // The blackListed widgets are those that have been filtered during the SPY mode with the
                //CAPS_LOCK + SHIFT + Click clickfilter functionality.
                if (!blackListed(w)){

                    //For widgets that are:
                    // - clickable
                    // and
                    // - unFiltered by any of the regular expressions in the Filter-tab, or
                    // - whitelisted using the clickfilter functionality in SPY mode (CAPS_LOCK + SHIFT + CNTR + Click)
                    // We want to create actions that consist of left clicking on them
                    if(isClickable(w) && (isUnfiltered(w) || whiteListed(w))) {
                        //Create a left click action with the Action Compiler, and add it to the set of derived actions
                        actions.add(ac.leftClickAt(w));
                    }

                    //For widgets that are:
                    // - typeable
                    // and
                    // - unFiltered by any of the regular expressions in the Filter-tab, or
                    // - whitelisted using the clickfilter functionality in SPY mode (CAPS_LOCK + SHIFT + CNTR + Click)
                    // We want to create actions that consist of typing into them
                    if(isTypeable(w) && (isUnfiltered(w) || whiteListed(w))) {
                        
                        // BLUEJ
                        // Only derive Type Action in UIAEdit widgets, we have lot of residual UIAText
                        if(w.get(Tags.Role, Roles.Widget).toString().equalsIgnoreCase("UIAEdit")){
                            //Create a type action with the Action Compiler, and add it to the set of derived actions
                            actions.add(ac.clickTypeInto(w, this.getRandomText(w), true));
                        }
                        
                    }
                    //Add sliding actions (like scroll, drag and drop) to the derived actions
                    //method defined below.
                    addSlidingActions(actions,ac,SCROLL_ARROW_SIZE,SCROLL_THICK,w, state);
                }
            }
        }
		//------------------------------------------------------------------------------------------------------------------------END FROM DESKTOP_GENERIC Z
		
		
		

		//return the set of derived actions
		return actions;
	}
	
			/**
	 * SwingSet2 application contains a TabElement called "SourceCode"
	 * that internally contains UIAEdit widgets that are not modifiable.
	 * Because these widgets have the property ToolTipText with the value "text/html",
	 * use this Tag to recognize and ignore.
	 */
	 /*
	private boolean isSourceCodeEditWidget(Widget w) {
		return w.get(Tags.ToolTipText, "").contains("text/html");
	}
	*/

	/**
	 * Select one of the available actions using an action selection algorithm (for example random action selection)
	 *
	 * super.selectAction(state, actions) updates information to the HTML sequence report
	 *
	 * @param state the SUT's current state
	 * @param actions the set of derived actions
	 * @return  the selected action (non-null!)
	 */
	@Override
	protected Action selectAction(State state, Set<Action> actions){
		return(super.selectAction(state, actions));
	}
	
	
	/*
		//Force actions on Tree widgets with a wrong accessibility
	public void widgetTree(Widget w, Set<Action> actions) {
		StdActionCompiler ac = new AnnotatingActionCompiler();
		actions.add(ac.leftClickAt(w));
		w.set(Tags.ActionSet, actions);
		for(int i = 0; i<w.childCount(); i++) {
			widgetTree(w.child(i), actions);
		}

	}
	*/
	
	

	/**
	 * Execute the selected action.
	 * Extract and create JaCoCo coverage report (After each action JaCoCo report will be created).
	 */
	@Override
	protected boolean executeAction(SUT system, State state, Action action){
		boolean actionExecuted = super.executeAction(system, state, action);

		// Only create JaCoCo report for Generate Mode
		// Modify if desired
		if(settings.get(ConfigTags.Mode).equals(Modes.Generate)) {

			// Dump the jacoco report from the remote JVM and Get the name/path of this file
			try {
				System.out.println("Extract JaCoCO report for Action number: " + actionCount);
				
				// Write sequence duration to CLI and to file
				long  sequenceDurationSoFar = System.currentTimeMillis() - startSequenceTime;
				System.out.println();
				System.out.println("Elapsed time until action " + actionCount + ": " + sequenceDurationSoFar);
	
				long minutes = (sequenceDurationSoFar / 1000)  / 60;
				int seconds = (int)((sequenceDurationSoFar / 1000) % 60);
				System.out.println("Elapsed time until action " + actionCount + ": " + + minutes + " minutes, "+ seconds + " seconds.");
				System.out.println();
				// Write sequence duration to file
				try {
					FileWriter myWriter = new FileWriter(reportTimeDir + "/" + OutputStructure.startInnerLoopDateString + "_" + OutputStructure.executedSUTname + "_actionTimeStamps.txt", true);
					//myWriter.write("Elapsed time until action " + actionCount + " : " + minutes + " minutes, " + seconds + " seconds   (" + sequenceDurationSoFar + " mili). \r\n");
					myWriter.write(sequenceDurationSoFar + "\r\n");
					myWriter.close();
					System.out.println("Wrote time so far to file." + reportTimeDir + "/_sequenceTimeUntilAction.txt");
				} catch (IOException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				
				String jacocoFile = MBeanClient.dumpJaCoCoActionStepReport(Integer.toString(actionCount));
				System.out.println("Extracted: " + new File(jacocoFile).getCanonicalPath());

				// Create JaCoCo report inside output\SUTexecuted folder
				String reportDir = new File(OutputStructure.outerLoopOutputDir).getCanonicalPath() 
						+ File.separator + "JaCoCo_reports"
						+ File.separator + OutputStructure.startInnerLoopDateString + "_" + OutputStructure.executedSUTname
						+ "_action_" + actionCount;

				// Launch JaCoCo report (build.xml) and overwrite desired parameters
				String antCommand = "cd jacoco && ant report"
						+ " -DjacocoFile=" + new File(jacocoFile).getCanonicalPath()
						+ " -DreportCoverageDir=" + reportDir;

				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", antCommand);
				Process p = builder.start();
				p.waitFor();

				System.out.println("JaCoCo report created : " + reportDir);

				String coverageInfo = new JacocoReportReader(reportDir).obtainHTMLSummary();
				System.out.println(coverageInfo);

			} catch (Exception e) {
				System.out.println("ERROR Creating JaCoCo covergae for specific action: " + actionCount);
			}

		}

		return actionExecuted;
	}

	/**
	 * TESTAR uses this method to determine when to stop the generation of actions for the
	 * current sequence. You can stop deriving more actions after:
	 * - a specified amount of executed actions, which is specified through the SequenceLength setting, or
	 * - after a specific time, that is set in the MaxTime setting
	 * @return  if <code>true</code> continue generation, else stop
	 */
	@Override
	protected boolean moreActions(State state) {
		return super.moreActions(state);
	}


	/**
	 * TESTAR uses this method to determine when to stop the entire test sequence
	 * You could stop the test after:
	 * - a specified amount of sequences, which is specified through the Sequences setting, or
	 * - after a specific time, that is set in the MaxTime setting
	 * @return  if <code>true</code> continue test, else stop
	 */
	@Override
	protected boolean moreSequences() {
		return super.moreSequences();
	}
	
	
	/**
	 * This method is invoked each time the TESTAR has reached the stop criteria for generating a sequence.
	 * This can be used for example for graceful shutdown of the SUT, maybe pressing "Close" or "Exit" button
	 */
	@Override
	protected void finishSequence() {

		// Only create JaCoCo report for Generate Mode
		// Modify if desired
		if(settings.get(ConfigTags.Mode).equals(Modes.Generate)) {

			// Dump the jacoco report from the remote JVM and Get the name/path of this file
			String jacocoFile = dumpAndGetJacocoSequenceFileName();

			// Create the output Jacoco report
			createJacocoSequenceReport(jacocoFile);

		}
 
		super.finishSequence();
		
		// Write sequence duration to CLI and to file
		long  sequenceDuration = System.currentTimeMillis() - startSequenceTime;
		System.out.println();
		System.out.println("Sequence duration: " + sequenceDuration);
		long minutes = (sequenceDuration / 1000)  / 60;
		int seconds = (int)((sequenceDuration / 1000) % 60);
		System.out.println("Sequence duration: " + minutes + " minutes, "+ seconds + " seconds.");
		System.out.println();
		try {
			String reportDir = new File(OutputStructure.outerLoopOutputDir).getCanonicalPath();//  + File.separator;
			FileWriter myWriter = new FileWriter(reportDir + "/" + OutputStructure.startInnerLoopDateString + "_" + OutputStructure.executedSUTname + "_sequenceDuration.txt");
			myWriter.write("Sequence duration: " + minutes + " minutes, " + seconds + " seconds.   (" + sequenceDuration + " mili)");
			myWriter.close();
			System.out.println("Wrote time to file." + reportDir + "/_sequenceDuration.txt");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	
	
	
	

	/**
	 * TESTAR has finished executing the actions
	 * Call MBeanClient to dump a jacoco.exec file
	 * 
	 * @return
	 */
	private String dumpAndGetJacocoSequenceFileName() {
		// Default name (generated by default by JaCoCo)
		String jacocoFile = "jacoco.exec";

		try {
			System.out.println("Extract JaCoCO report with MBeanClient...");
			jacocoFile = MBeanClient.dumpJaCoCoSequenceReport();
			System.out.println("Extracted: " + new File(jacocoFile).getCanonicalPath());
		} catch (Exception e) {
			System.out.println("ERROR: MBeanClient was not able to dump the JaCoCo exec report");
		}

		return jacocoFile;
	}

	/**
	 * With the dumped jacocoFile (typical jacoco.exec) 
	 * and the build.xml file (that includes a reference to the .class SUT files).
	 * 
	 * Create the JaCoCo report files.
	 * 
	 * @param jacocoFile
	 */
	private void createJacocoSequenceReport(String jacocoFile) {
		try {
			// JaCoCo report inside output\SUTexecuted folder
			String reportDir = new File(OutputStructure.outerLoopOutputDir).getCanonicalPath() 
					+ File.separator + "JaCoCo_reports"
					+ File.separator + OutputStructure.startInnerLoopDateString + "_" + OutputStructure.executedSUTname;

			// Launch JaCoCo report (build.xml) and overwrite desired parameters
			String antCommand = "cd jacoco && ant report"
					+ " -DjacocoFile=" + new File(jacocoFile).getCanonicalPath()
					+ " -DreportCoverageDir=" + reportDir;

			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", antCommand);
			Process p = builder.start();
			p.waitFor();

			System.out.println("JaCoCo report created : " + reportDir);

			String coverageInfo = new JacocoReportReader(reportDir).obtainHTMLSummary();
			System.out.println(coverageInfo);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This methods stops the SUT
	 *
	 * @param system
	 */
	@Override
	protected void stopSystem(SUT system) {
		super.stopSystem(system);

		// This is the default JaCoCo generated file, we dumped our desired file with MBeanClient (finishSequence)
		// In this protocol this one is residual, so just delete
		if(new File("jacoco.exec").exists()) {
			System.out.println("Deleted residual jacoco.exec file ? " + new File("jacoco.exec").delete());
		}
	}




	/**
	 * This methods is called after each test sequence, allowing for example using external profiling software on the SUT
	 *
	 * super.postSequenceProcessing() is adding test verdict into the HTML sequence report
	 */
	@Override
	protected void postSequenceProcessing() {
		super.postSequenceProcessing();
	}
}