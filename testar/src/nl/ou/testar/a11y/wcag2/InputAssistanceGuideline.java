/***************************************************************************************************
*
* Copyright (c) 2017 Open Universiteit - www.ou.nl
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


package nl.ou.testar.a11y.wcag2;

import nl.ou.testar.a11y.wcag2.SuccessCriterion.Level;

/**
 * A WCAG 2.0 guideline
 * @author Davy Kager
 *
 */
public final class InputAssistanceGuideline extends AbstractGuideline {

	private static final long serialVersionUID = -3766907198803540591L;

	InputAssistanceGuideline(AbstractPrinciple parent) {
		super(3, "Input Assistance", parent);
		criteria.add(new SuccessCriterion(1, "Error Identification",
				this, Level.A, "minimize-error-identified"));
		criteria.add(new SuccessCriterion(2, "Labels or Instructions",
				this, Level.A, "minimize-error-cues"));
		criteria.add(new SuccessCriterion(3, "Error Suggestion",
				this, Level.AA, "minimize-error-suggestions"));
		criteria.add(new SuccessCriterion(4, "Error Prevention (Legal, Financial, Data)",
				this, Level.AA, "minimize-error-reversible"));
	}

}
