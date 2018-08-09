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

import com.tmobile.test.entity.Transaction;
import com.tmobile.test.exception.ParsingException;
import com.tmobile.test.parser.FileParser;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for solving the main problem from the test.
 *
 * @author jakro01
 */
public class Solution {

	private static final Logger logger = LogManager.getLogger("Solution");
	private static final String SEPARATOR = "|";

	/**
	 * Creates required output string after parsing give file.
	 *
	 * @param location relative or absolute path to file
	 *
	 * @return required string from parsed file
	 */
	public String solution(String location) {
		List<Transaction> transactions = null;
		try {
			transactions = new FileParser().parseFile(location);
		} catch (ParsingException ex) {
			logger.error("There was problem with parsing file {}", location, ex);
			throw new RuntimeException();
		}

		Map<String, List<Transaction>> mapOfEntries = createMapWithOrder(transactions);

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < transactions.size(); i++) {
			Transaction transaction = transactions.get(i);
			builder.append(transaction.getPartner().getCompanyName()).append(SEPARATOR)
						 .append(getOrderOfEntryByCompany(transaction, mapOfEntries)).append(SEPARATOR)
						 .append(transaction.getDescription());

			if (i != transactions.size() - 1) {
				builder.append(System.lineSeparator());
			}
		}

		return builder.toString();
	}

	/**
	 * Creates temp map used for aggregation of transactions based on company name and their sorting
	 * based on the date of transaction.
	 *
	 * @param transactions list of transaction that should be aggregated and sorted
	 *
	 * @return aggregated map of transactions
	 */
	private Map<String, List<Transaction>> createMapWithOrder(List<Transaction> transactions) {
		Map<String, List<Transaction>> map = new HashMap<>();

		for (Transaction transaction : transactions) {
			String companyName = transaction.getPartner().getCompanyName();
			if (!map.containsKey(companyName)) {
				List<Transaction> list = new ArrayList<>();
				list.add(transaction);
				map.put(companyName, list);
			} else {
				map.get(companyName).add(transaction);
			}
		}

		map.forEach((k, v) -> v.sort(Comparator.comparing(Transaction::getTimestamp)));
		return map;
	}

	/**
	 * Returns order of the given transaction based on the given aggregated map of transactions. As
	 * transactions are stored in List and we are working with their index in the list for determining
	 * their order but solution expect start counting from 1 and not 0, we need to add 1 to the index
	 * of transaction.
	 *
	 * @param transaction transaction
	 * @param map         aggregated and sorted map of transactions
	 *
	 * @return formatted string containing number in transaction in sorted list starting with 1 and
	 * padded with zeroes based on the number of transactions
	 */
	private String getOrderOfEntryByCompany(Transaction transaction,
			Map<String, List<Transaction>> map) {
		List<Transaction> orderedEntries = map.get(transaction.getPartner().getCompanyName());

		// This is really dirty hack but I just can find out any other way how to get length of number -> maybe some math function?
		return String.format("%0" + String.valueOf(orderedEntries.size()).length() + "d",
				orderedEntries.indexOf(transaction) + 1);
	}

}
