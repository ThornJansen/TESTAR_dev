package nl.ou.testar.tgherkin;

import java.util.ArrayList;
import java.util.List;

import nl.ou.testar.tgherkin.gen.TgherkinParser;
import nl.ou.testar.tgherkin.gen.TgherkinParserBaseVisitor;

/**
 * TgherkinImageAnalyzer uses the visitor patter to traverse the parse result and 
 * returns a list of specified image file names.   
 * The TgherkinParserBaseVisitor superclass is generated by ANTLR. 
 *
 */
public class TgherkinImageFileAnalyzer extends TgherkinParserBaseVisitor<Object> {

	private List<String> imageList = new ArrayList<String>();

	@Override 
	public List<String> visitDocument(TgherkinParser.DocumentContext ctx) { 
		for (TgherkinParser.FeatureContext featureContext : ctx.feature()){
			visitFeature(featureContext);
		}
		return imageList;
	}
	
	@Override 
	public Object visitImageFunction(TgherkinParser.ImageFunctionContext ctx) { 
		// unquote 
		String imageFile = ctx.STRING().getText().substring(1, ctx.STRING().getText().length()-1);
		imageList.add(imageFile); 
		return visitChildren(ctx); 
	}
	
}