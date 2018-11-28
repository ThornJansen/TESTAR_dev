package tgherkin_subroutine_dtg;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import nl.ou.testar.tgherkin.model.SubroutineProxy;
import nl.ou.testar.tgherkin.protocol.Report;
import nl.ou.testar.tgherkin.protocol.SubroutineProtocol;

import org.fruit.alayer.Action;
import org.fruit.alayer.SUT;
import org.fruit.alayer.State;
import org.fruit.alayer.Tags;
import org.fruit.alayer.Verdict;
import org.fruit.alayer.Widget;
import org.fruit.alayer.actions.StdActionCompiler;
import org.fruit.alayer.exceptions.ActionBuildException;
import org.fruit.monkey.ConfigTags;
import org.fruit.monkey.Settings;

/**
 * Class responsible for executing a SubroutineProtocol with
 * a subroutine for web form on the ATI site.
 *
 * @author Conny Hageluken
 * @Date October 2018
 */
public class Protocol_subroutine_dtg 
    extends SubroutineProtocol 
    implements SubroutineProxy {

  /**
   * Default constructor.
   */
  public Protocol_subroutine_dtg() {
    //default constructor
  }
  
  /**
   * Start up the subroutine only once.
   */
  private int onlyOnce = 1;

  /**
   * State is fulfilling criterion for running a subroutine.
   * Important to define in this method
     * - setSourceFile: a valid Tgherkin subroutine filename
     * - setActualIndexSD: index of the actual subroutine
   * @param state the SUT's current state
     * @return state is ready for subroutine action
   */
  @Override
  public boolean startState(State state) {
    if (onlyOnce == 1) {
      for (Widget widget : getTopWidgets(state)) {
        String title = widget.get(Tags.Title, null).toString();
        if (title.equalsIgnoreCase(getAddressTitle())) {
          String value = widget.get(Tags.ValuePattern, null);
          for (Integer index: getSubData().keySet()) {
            String subStr = getSubData().get(index)[0];
            if (value != null && value.contains(subStr)) {
              setActualIndexSD(index);
              try {
                setSourceFile("./" + getProtocolFolder() + "/" + getSubData().get(index)[1]);
              } catch (Exception e) {
                e.printStackTrace();
              }
              // report header
              Report.report(
                  null, null, null, settings().get(ConfigTags.GenerateTgherkinReport), false);
              onlyOnce++;
              return true;
            }
          }
          break;
        }
      }
    }
    return false;
  }

  /**
   * This method is invoked each time TESTAR starts to generate a new subroutine.
   * - start a subroutine with this document (initializeDocument())
   * @param state the SUT's current state
   */
  @Override
  public void startSubroutine(State state) {
    super.initializeDocument();
    super.startSubroutine(state);
  }

  /** Define action to be taken when switching from subroutine to .
     * @param state the SUT's current state
   * @return the action to be taken
    */
  @Override
  public Set<Action> finishState(State state) {
    Set<Action> actions = new HashSet<Action>();

    Action action = null;
    StdActionCompiler ac = new StdActionCompiler();

    for (Widget widget : getTopWidgets(state)) {
      String title = widget.get(Tags.Title, null);
      if ("feedback".equalsIgnoreCase(title)) {
        action = ac.leftClickAt(widget);
      }
    } 
    actions.add(action);
    return actions;
  }

  /** 
   * This method is invoked each time after TESTAR finishes the generation of a subroutine.
   */
  @Override
  public void finishSubroutine(State state) {
    super.finishSubroutine(state);
  }

  /** 
   * Called once during the life time of TESTAR.
   * This method can be used to perform initial setup work
   * @param   settings   the current TESTAR settings as specified by the user.
   */
  @Override
  protected void initialize(Settings settings) {
    super.initialize(settings);
    readSubroutineDataInputfile();
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
  protected Set<Action> deriveActions(SUT system, State state) throws ActionBuildException {
    return super.deriveActions(system, state);
  }

  /**
   * This is a helper method used by the default implementation of <code>buildState()</code>.
   * It examines the SUT's current state and the subroutine verdict
   * @return oracle verdict, which determines whether the state is erroneous and why.
   */
  @Override
  protected Verdict getVerdict(State state) {
    return super.getVerdict(state);
  }

  /**
   * Select one of the possible actions (e.g. at random).
   * @param state the SUT's current state
   * @param actions the set of available actions as computed by <code>buildActionsSet()</code>
   * @return  the selected action (non-null!)
   */
  @Override
  protected Action selectAction(State state, Set<Action> actions) { 
    return super.selectAction(state, actions);
  }

  /**
   * Execute the selected action.
   * @param system the SUT
   * @param state the SUT's current state
   * @param action the action to execute
   * @return whether or not the execution succeeded
   */
  @Override
  protected boolean executeAction(SUT system, State state, Action action) {
    return super.executeAction(system, state, action);
  }

  /**
   * TESTAR uses this method to determine when to stop the generation of actions for the
   * current sequence. You could stop the sequence's generation after a given amount of executed
   * actions or after a specific time etc.
   * @return  if <code>true</code> continue generation, else stop
   */
  @Override
  protected boolean moreActions(State state) {
    return super.moreActions(state);
  }

  /** 
   * This method is invoked each time after TESTAR finished the generation of a sequence.
   */
  @Override
  protected void finishSequence(File recordedSequence) {
    onlyOnce = 1;
    super.finishSequence(recordedSequence);
  }

  /**
   * TESTAR uses this method to determine when to stop the entire test.
   * You could stop the test after a given amount of generated sequences or
   * after a specific time etc.
   * @return  if <code>true</code> continue test, else stop   */
  @Override
  protected boolean moreSequences() {
    return super.moreSequences();
  }
}