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

package com.tmobile.test.parser;


import static com.tmobile.test.entity.Transaction.DATE_FORMAT;
import static org.junit.Assert.assertEquals;

import com.tmobile.test.entity.Transaction;
import com.tmobile.test.exception.ParsingException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Unit tests for {@link FileParser}.
 *
 * @author jakro01
 */
public class FileParserTest {

	private Parser parser = new FileParser();

	@Test(expected = ParsingException.class)
	public void testParseFilesNotExistingFile() throws ParsingException {
		parser.parseFile("fileDoesntExist.txt");
	}

	@Test(expected = NotImplementedException.class)
	public void testParseMultipleFiles() throws ParsingException {
		List<Transaction> transactionList = parser
				.parseFiles("src/test/resources/test1.txt", "src/test/resources/test2.txt");

		assertEquals(15, transactionList.size());
	}

	@Test
	public void testParseFile() throws ParsingException {
		List<Transaction> transactionList = parser
				.parseFile("src/test/resources/hugeFile.txt");

		assertEquals(27, transactionList.size());
	}

	@Test(expected = ParsingException.class)
	public void testParseInvalidFile() throws ParsingException {
		List<Transaction> transactionList = parser.parseFile("src/test/resources/invalidContent.txt");
	}
}
