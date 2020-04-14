/***************************************************************************************************
 *
 * Copyright (c) 2019, 2020 Universitat Politecnica de Valencia - www.upv.es
 * Copyright (c) 2019, 2020 Open Universiteit - www.ou.nl
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

package org.testar.json.object;

import java.io.File;
import java.io.FileWriter;
import java.util.SortedSet;

import org.fruit.monkey.Settings;
import org.fruit.monkey.SettingsDialog;
import org.testar.OutputStructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.upv.staq.testar.NativeLinker;

public class JsonArtefactTestResults {
	
	private JsonArtefactTestResults() {}
	
	private static String url = "https://testar.org/images/models/";

	public static void createTestResultsArtefact(Settings settings, SortedSet<String> sequencesOutputDir,
			SortedSet<String> logsOutputDir, SortedSet<String> htmlOutputDir, SortedSet<String> sequencesVerdicts,
			SortedSet<String> coverageSummary, SortedSet<String> coverageDir) {

		SettingsJsonObject settingJson = new SettingsJsonObject(settings);

		SutJsonObject sutJson = new SutJsonObject("sutTitle", "sutName", true, "license", "sutURL", "1.X.X",
				NativeLinker.getOsName());

		ToolJsonObject toolJson = new ToolJsonObject("TESTAR", "TESTAR: Automated Robustness Testing at the GUI level",
				true, "BSD-3-Clause License", "https://github.com/TESTARtool/TESTAR_dev/tree/decoder_pkm", SettingsDialog.TESTAR_VERSION,
				NativeLinker.getOsName());

		TestResultsJsonObject resultsJson = new TestResultsJsonObject(OutputStructure.startOuterLoopDateString,
				url, sutJson, toolJson, settingJson);
		
		if(!sequencesOutputDir.isEmpty())
			resultsJson.setSequencesResult(sequencesOutputDir);
		if(!logsOutputDir.isEmpty())
			resultsJson.setLogsResult(logsOutputDir);
		if(!htmlOutputDir.isEmpty())
			resultsJson.setHtmlsResult(htmlOutputDir);
		if(!sequencesVerdicts.isEmpty())
			resultsJson.setSequencesVerdicts(sequencesVerdicts);
		if(!coverageSummary.isEmpty())
			resultsJson.setCoverageSummary(coverageSummary);
		if(!coverageDir.isEmpty())
			resultsJson.setCoverageDirectory(coverageDir);


		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String outputPath = OutputStructure.outerLoopOutputDir + File.separator +
				"ArtefactTestResults_"  + OutputStructure.executedSUTname + "_" +
				OutputStructure.startOuterLoopDateString + ".json";

		try{
			FileWriter fileWriter = new FileWriter(outputPath);
			gson.toJson(resultsJson, fileWriter);
			fileWriter.flush();
			fileWriter.close();
		}catch(Exception e){
			System.out.println("ERROR! Creating JSON ArtefactTestResults!");
		}
	}

}
