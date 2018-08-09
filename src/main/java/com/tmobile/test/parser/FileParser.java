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

import com.tmobile.test.entity.Transaction;
import com.tmobile.test.exception.ParsingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Implementation of {@link Parser} interface used for parsing files and converting their
 * contents to the {@link Transaction} POJO.
 *
 * @author jakro01
 */
public class FileParser implements Parser {

	private static final Logger logger = LogManager.getLogger("FileParser");

	@Override
	public List<Transaction> parseFiles(String... fileLocations) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public List<Transaction> parseFile(String fileLocation) throws ParsingException {
		List<Transaction> entries;
		logger.debug("Starting parsing file: {}", Paths.get(fileLocation));

		try (Stream<String> stream = Files.lines(Paths.get(fileLocation))) {
			// I generally don't like FP in code because of maintainability but here, it is pretty
			// straightforward and easy to understand and much faster for this prototype
			entries = new ArrayList<>(
					stream.map(Transaction::createTransactionFromString).collect(Collectors.toList()));

		} catch (IOException ex) {
			throw new ParsingException(
					"Problem with reading file " + Paths.get(fileLocation).toAbsolutePath(), ex);
		} catch (IllegalArgumentException | DateTimeParseException ex) {
			throw new ParsingException(
					"File " + Paths.get(fileLocation).toAbsolutePath() + " contains invalid content!", ex);
		}

		return entries;
	}
}
