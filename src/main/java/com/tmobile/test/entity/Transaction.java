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

package com.tmobile.test.entity;

import com.tmobile.test.exception.ParsingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * POJO class representing class. Prepared for the some database integration.
 *
 * @author jakro01
 */
public class Transaction {

	private static final Logger logger = LogManager.getLogger("Transaction");

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private Long id;
	private Partner partner;
	private String description;
	private LocalDateTime timestamp;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Transaction)) {
			return false;
		}
		Transaction that = (Transaction) o;
		return id == that.id &&
				Objects.equals(partner, that.partner) &&
				Objects.equals(description, that.description) &&
				Objects.equals(timestamp, that.timestamp);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, partner, description, timestamp);
	}

	/**
	 * Creates a {@link Transaction} object from given string. The expected string should be in format
	 * "name of transaction, partner/client phone number, yyyy-mm-dd hh:mm:ss". If one of the strings
	 * contains specials chars ('/', ',') then it should be envelope in double quotes.
	 *
	 * @param line string for parsing
	 *
	 * @return Transaction object created from given string
	 *
	 * @throws ParsingException if there were some problems with parsing, like empty line or invalid
	 *                          format of string
	 */
	public static Transaction createTransactionFromString(String line) {
		if (line.isEmpty()) {
			throw new IllegalArgumentException("There cannot be a empty line");
		}
		logger.trace("Starting parsing line: " + line);

		Transaction transaction = new Transaction();

		// I will be honest I found this regex on the stackoverflow...and I must say it is really crazy regex
		String[] split = line.trim().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		if (split.length != 3) {
			throw new IllegalArgumentException("Unable to parse invalid transaction line: " + line);
		}
		String description = split[0].trim();
		description =
				description.startsWith("\"") && description.endsWith("\"") ? description.replace("\"", "")
						: StringUtils.normalizeSpace(description);
		transaction.setDescription(description);

		Partner partner = new Partner();
		String companyName = StringUtils.substringBeforeLast(split[1], "/").trim();
		companyName =
				companyName.startsWith("\"") && companyName.endsWith("\"") ? companyName.replace("\"", "")
						: StringUtils.normalizeSpace(companyName);
		partner.setCompanyName(companyName);

		String phoneNumber = StringUtils.substringAfterLast(split[1], "/").trim();
		phoneNumber =
				phoneNumber.startsWith("\"") && phoneNumber.endsWith("\"") ? phoneNumber.replace("\"", "")
						: StringUtils.normalizeSpace(phoneNumber);
		partner.setPhoneNumber(phoneNumber);

		transaction.setPartner(partner);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDateTime dateTime = LocalDateTime
				.parse(StringUtils.normalizeSpace(split[2].trim()), formatter);
		transaction.setTimestamp(dateTime);

		return transaction;
	}
}
