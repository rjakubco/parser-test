/******************************************************************************
 *
 * Copyright (c) 2018 CA.  All rights reserved.
 *
 * This software and all information contained therein is confidential and
 * proprietary and shall not be duplicated, used, disclosed or disseminated
 * in any way except as authorized by the applicable license agreement,
 * without the express written permission of CA. All authorized reproductions
 * must be marked with this language.
 *
 * EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT
 * PERMITTED BY APPLICABLE LAW, CA PROVIDES THIS SOFTWARE WITHOUT
 * WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY IMPLIED
 * WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN
 * NO EVENT WILL CA BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY
 * LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM THE USE OF THIS SOFTWARE,
 * INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
 * GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR
 * DAMAGE.
 *
 ******************************************************************************/

package com.tmobile.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for {@link Solution} class.
 *
 * @author jakro01
 */
public class SolutionTest {

	@Test(expected = RuntimeException.class)
	public void testSolutionInvalidFile() {
		new Solution().solution("src/test/resources/invalidContent.txt");
	}

	@Test(expected = RuntimeException.class)
	public void testSolutionNotExistingFile() {
		new Solution().solution("notExistingFile");
	}

	@Test
	public void testSolutionCorrectFile() {
		String expectedString = "Netflix|2|payment weekly\n"
				+ "Apple|1|game Of Thrones\n"
				+ "Netflix|1|payment yearly\n"
				+ "Microsoft|2|Office 365\n"
				+ "Microsoft|1|Office 365\n"
				+ "Apple|2|payment weekly";
		
		String response = new Solution().solution("src/test/resources/test1.txt");
		
		assertEquals(expectedString, response);
	}

	@Test
	public void testSolutionGeneratingPaddedNumbering() {
		String response = new Solution().solution("src/test/resources/hugeFile.txt");

		assertTrue(response.contains("Microsoft|01"));
	}
}
