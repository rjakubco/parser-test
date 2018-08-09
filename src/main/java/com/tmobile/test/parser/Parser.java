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
import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Interface representing parser for files.
 *
 * @author jakro01
 */
public interface Parser {

	/**
	 * Parses given file on given location and parses its content to {@link Transaction}
	 * objects and stores them inside {@link List}.
	 *
	 * @param fileLocation location of files for parsing
	 *
	 * @return list of parsed transaction from files
	 */
	List<Transaction> parseFile(String fileLocation) throws ParsingException;


	/**
	 * Parses all given files on given locations and parses their contents to {@link Transaction}
	 * objects and stores them inside ordered {@link List}.
	 *
	 * @param fileLocations locations of files for parsing
	 *
	 * @return list of parsed transaction from files
	 */
	List<Transaction> parseFiles(String... fileLocations) throws NotImplementedException;
}
