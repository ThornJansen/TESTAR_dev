package nl.ou.testar.tgherkin;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import nl.ou.testar.tgherkin.gen.TgherkinParser;
import nl.ou.testar.tgherkin.gen.TgherkinParserBaseVisitor;
import nl.ou.testar.tgherkin.gen.TgherkinParser.HitKeyArgumentContext;

/**
 * TgherkinTokenAnalyzer analyzes a Tgherkin text for usage within an editor.
 * Tgherkin elements like title and narrative are intended for natural language expressions
 * These elements can therefore contain Lexer tokens, but these tokens have no special
 * meaning within a title or narrative.
 * This class uses the visitor patter to traverse the parse result and 
 * returns a list of indices to tokens that do have a special meaning.   
 * The editor can use this info to display those relevant tokens in a particular style (eg coloring)
 * The TgherkinParserBaseVisitor superclass is generated by ANTLR. 
 *
 */
public class TgherkinTokenAnalyzer extends TgherkinParserBaseVisitor<Object> {

	private List<Integer> tokenList = new ArrayList<Integer>();

	@Override 
	public List<Integer> visitDocument(TgherkinParser.DocumentContext ctx) { 
		for (TgherkinParser.FeatureContext featureContext : ctx.feature()) {
			visitFeature(featureContext);
		}
		return tokenList;
	}

	@Override 
	public Object visitFeature(TgherkinParser.FeatureContext ctx) {
		if (ctx.FEATURE_KEYWORD() != null) {
			tokenList.add(ctx.FEATURE_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitBackground(TgherkinParser.BackgroundContext ctx) { 
		if (ctx.BACKGROUND_KEYWORD() != null) {
			tokenList.add(ctx.BACKGROUND_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitScenario(TgherkinParser.ScenarioContext ctx) {
		if (ctx.SCENARIO_KEYWORD() != null) {
			tokenList.add(ctx.SCENARIO_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitScenarioOutline(TgherkinParser.ScenarioOutlineContext ctx) { 
		if (ctx.SCENARIO_OUTLINE_KEYWORD() != null) {
			tokenList.add(ctx.SCENARIO_OUTLINE_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitExamples(TgherkinParser.ExamplesContext ctx) { 
		if (ctx.EXAMPLES_KEYWORD() != null) {
			tokenList.add(ctx.EXAMPLES_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitTable(TgherkinParser.TableContext ctx) { 
		for (TerminalNode terminalNode : ctx.TABLE_ROW()) {
			tokenList.add(terminalNode.getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitSelection(TgherkinParser.SelectionContext ctx) {
		if (ctx.SELECTION_KEYWORD() != null) {
			tokenList.add(ctx.SELECTION_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitOracle(TgherkinParser.OracleContext ctx) {
		if (ctx.ORACLE_KEYWORD() != null) {
			tokenList.add(ctx.ORACLE_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitStep(TgherkinParser.StepContext ctx) {
		if (ctx.STEP_KEYWORD() != null) {
			tokenList.add(ctx.STEP_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitGivenClause(TgherkinParser.GivenClauseContext ctx) {
		if (ctx.STEP_GIVEN_KEYWORD() != null) {
			tokenList.add(ctx.STEP_GIVEN_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitThenClause(TgherkinParser.ThenClauseContext ctx) {
		if (ctx.STEP_THEN_KEYWORD() != null) {
			tokenList.add(ctx.STEP_THEN_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitWhenClause(TgherkinParser.WhenClauseContext ctx) {
		if (ctx.STEP_WHEN_KEYWORD() != null) {
			tokenList.add(ctx.STEP_WHEN_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitStepRange(TgherkinParser.StepRangeContext ctx) { 
		if (ctx.STEP_RANGE_KEYWORD() != null) {
			tokenList.add(ctx.STEP_RANGE_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}	

	@Override 
	public Object visitStepWhile(TgherkinParser.StepWhileContext ctx) {
		if (ctx.STEP_WHILE_KEYWORD() != null) {
			tokenList.add(ctx.STEP_WHILE_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}	

	@Override 
	public Object visitStepRepeatUntil(TgherkinParser.StepRepeatUntilContext ctx) {
		if (ctx.STEP_REPEAT_KEYWORD() != null) {
			tokenList.add(ctx.STEP_REPEAT_KEYWORD().getSourceInterval().a);
		}
		if (ctx.STEP_UNTIL_KEYWORD() != null) {
			tokenList.add(ctx.STEP_UNTIL_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}	

	@Override 
	public Object visitStepNOP(TgherkinParser.StepNOPContext ctx) {
		if (ctx.STEP_NOP_KEYWORD() != null) {
			tokenList.add(ctx.STEP_NOP_KEYWORD().getSourceInterval().a);
		}	
		return visitChildren(ctx); 
	}

	@Override 
	public Object visitWidgetTreeConditionEither(TgherkinParser.WidgetTreeConditionEitherContext ctx) {
		if (ctx.STEP_EITHER_KEYWORD() != null) {
			tokenList.add(ctx.STEP_EITHER_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitWidgetTreeConditionAlso(TgherkinParser.WidgetTreeConditionAlsoContext ctx) {
		if (ctx.STEP_ALSO_KEYWORD() != null) {
			tokenList.add(ctx.STEP_ALSO_KEYWORD().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitTypeGesture(TgherkinParser.TypeGestureContext ctx) { 
		if (ctx.TYPE_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.TYPE_NAME().getSourceInterval().a);
			}
		}
		if (ctx.PLACEHOLDER() != null) {
			tokenList.add(ctx.PLACEHOLDER().getSourceInterval().a);
		}
		if (ctx.STRING() != null) {
			tokenList.add(ctx.STRING().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override
	public Object visitClickGesture(TgherkinParser.ClickGestureContext ctx) {
		if (ctx.CLICK_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.CLICK_NAME().getSourceInterval().a);
			}
		}
		if (ctx.PLACEHOLDER() != null) {
			tokenList.add(ctx.PLACEHOLDER().getSourceInterval().a);
		} else {
			if (ctx.TRUE() != null) {
				tokenList.add(ctx.TRUE().getSourceInterval().a);
			} else {
				if (ctx.FALSE() != null) {
					tokenList.add(ctx.FALSE().getSourceInterval().a);
				}
			}
		}
		return visitChildren(ctx);
	}

	@Override
	public Object visitDoubleClickGesture(TgherkinParser.DoubleClickGestureContext ctx) {
		if (ctx.DOUBLE_CLICK_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.DOUBLE_CLICK_NAME().getSourceInterval().a);
			}
		}
		if (ctx.PLACEHOLDER() != null) {
			tokenList.add(ctx.PLACEHOLDER().getSourceInterval().a);
		} else {
			if (ctx.TRUE() != null) {
				tokenList.add(ctx.TRUE().getSourceInterval().a);
			} else {
				if (ctx.FALSE() != null) {
					tokenList.add(ctx.FALSE().getSourceInterval().a);
				}
			}
		}
		return visitChildren(ctx);
	}

	@Override
	public Object visitTripleClickGesture(TgherkinParser.TripleClickGestureContext ctx) {
		if (ctx.TRIPLE_CLICK_NAME() != null && ctx.LPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.TRIPLE_CLICK_NAME().getSourceInterval().a);
			}	
		}
		if (ctx.PLACEHOLDER() != null) {
			tokenList.add(ctx.PLACEHOLDER().getSourceInterval().a);
		} else {
			if (ctx.TRUE() != null) {
				tokenList.add(ctx.TRUE().getSourceInterval().a);
			} else {
				if (ctx.FALSE() != null) {
					tokenList.add(ctx.FALSE().getSourceInterval().a);
				}
			}
		}
		return visitChildren(ctx);
	}

	@Override
	public Object visitAnyGesture(TgherkinParser.AnyGestureContext ctx) {
		if (ctx.ANY_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.ANY_NAME().getSourceInterval().a);
			}
		}
		if (ctx.PLACEHOLDER() != null) {
			tokenList.add(ctx.PLACEHOLDER().getSourceInterval().a);
		} else {
			if (ctx.TRUE() != null) {
				tokenList.add(ctx.TRUE().getSourceInterval().a);
			} else {
				if (ctx.FALSE() != null) {
					tokenList.add(ctx.FALSE().getSourceInterval().a);
				}
			}
		}
		return visitChildren(ctx);
	}

	@Override
	public Object visitHitKeyGesture(TgherkinParser.HitKeyGestureContext ctx) {
		if (ctx.HIT_KEY_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.HIT_KEY_NAME().getSourceInterval().a);
			}
		}
		for (HitKeyArgumentContext argumentCtx : ctx.hitKeyArgument()) {
			if (argumentCtx.KB_KEY_NAME() != null) {
				tokenList.add(argumentCtx.KB_KEY_NAME().getSourceInterval().a);
			}
			if (argumentCtx.PLACEHOLDER() != null) {
				tokenList.add(argumentCtx.PLACEHOLDER().getSourceInterval().a);
			}
		}
		return visitChildren(ctx);
	}

	@Override
	public Object visitDragDropGesture(TgherkinParser.DragDropGestureContext ctx) {
		if (ctx.DRAG_DROP_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.DRAG_DROP_NAME().getSourceInterval().a);
			}
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitParameterlessGesture(TgherkinParser.ParameterlessGestureContext ctx) {
		if (ctx.gestureName() == null) {
			return null;
		}	
		if (ctx.gestureName().DRAG_SLIDER_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.gestureName().DRAG_SLIDER_NAME().getSourceInterval().a);
			}
			return null;
		}
		if (ctx.gestureName().RIGHT_CLICK_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null ) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.gestureName().RIGHT_CLICK_NAME().getSourceInterval().a);
			}
			return null;
		}
		if (ctx.gestureName().MOUSE_MOVE_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.gestureName().MOUSE_MOVE_NAME().getSourceInterval().a);
			}
			return null;
		}
		if (ctx.gestureName().DROP_DOWN_AT_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.gestureName().DROP_DOWN_AT_NAME().getSourceInterval().a);
			}
			return null;
		}
		return null;
	}

	@Override 
	public Object visitMatchesFunction(TgherkinParser.MatchesFunctionContext ctx) {
		if (ctx.MATCHES_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.MATCHES_NAME().getSourceInterval().a);
			}
		}
		if (ctx.STRING() != null) {
			tokenList.add(ctx.STRING().getSourceInterval().a);
		}
		return visitChildren(ctx); 
	}

	@Override 
	public Object visitXpathFunction(TgherkinParser.XpathFunctionContext ctx) {
		if (ctx.XPATH_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.XPATH_NAME().getSourceInterval().a);
			}
		}
		if (ctx.STRING() != null) {
			tokenList.add(ctx.STRING().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override
	public Object visitImageFunction(TgherkinParser.ImageFunctionContext ctx) {
		if (ctx.IMAGE_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.IMAGE_NAME().getSourceInterval().a);
			}
		}
		if (ctx.STRING() != null) {
			tokenList.add(ctx.STRING().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitOcrFunction(TgherkinParser.OcrFunctionContext ctx) {
		if (ctx.OCR_NAME() != null && ctx.LPAREN() != null && ctx.RPAREN() != null) {
			if (ctx.LPAREN().getText().equals("(") && ctx.RPAREN().getText().equals(")")) {
				tokenList.add(ctx.OCR_NAME().getSourceInterval().a);
			}
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitBool(TgherkinParser.BoolContext ctx) {
		if (ctx.FALSE() != null) {
			tokenList.add(ctx.FALSE().getSourceInterval().a);
		}
		if (ctx.TRUE() != null) {
			tokenList.add(ctx.TRUE().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitLogicalVariable(TgherkinParser.LogicalVariableContext ctx) {
		if (ctx.BOOLEAN_VARIABLE() != null) {
			tokenList.add(ctx.BOOLEAN_VARIABLE().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitLogicalPlaceholder(TgherkinParser.LogicalPlaceholderContext ctx) {
		if (ctx.PLACEHOLDER() != null) {
			tokenList.add(ctx.PLACEHOLDER().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitIntegerConst(TgherkinParser.IntegerConstContext ctx) {
		if (ctx.INTEGER_NUMBER() != null) {
			tokenList.add(ctx.INTEGER_NUMBER().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitDecimalConst(TgherkinParser.DecimalConstContext ctx) {
		if (ctx.DECIMAL_NUMBER() != null) {
			tokenList.add(ctx.DECIMAL_NUMBER().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}
	
	@Override public Object visitNumericVariable(TgherkinParser.NumericVariableContext ctx) {
		if (ctx.NUMBER_VARIABLE() != null) {
			tokenList.add(ctx.NUMBER_VARIABLE().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitNumericPlaceholder(TgherkinParser.NumericPlaceholderContext ctx) { 
		if (ctx.PLACEHOLDER() != null) {
			tokenList.add(ctx.PLACEHOLDER().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitStringConst(TgherkinParser.StringConstContext ctx) { 
		if (ctx.STRING() != null) {
			tokenList.add(ctx.STRING().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitStringVariable(TgherkinParser.StringVariableContext ctx) { 
		if (ctx.STRING_VARIABLE() != null) {
			tokenList.add(ctx.STRING_VARIABLE().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

	@Override 
	public Object visitStringPlaceholder(TgherkinParser.StringPlaceholderContext ctx) { 
		if (ctx.PLACEHOLDER() != null) {
			tokenList.add(ctx.PLACEHOLDER().getSourceInterval().a);
		}
		return visitChildren(ctx);
	}

}