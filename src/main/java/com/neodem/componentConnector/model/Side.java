package com.neodem.componentConnector.model;

import org.apache.commons.lang3.StringUtils;

public enum Side {
	Right, Left;

	public static Side make(String name) {
		if (StringUtils.isNotBlank(name)) {
			if ("right".equals(name.toLowerCase())) {
				return Right;
			}
			return Left;

		}
		return null;
	}

	public Side other() {
		if (this == Right)
			return Left;
		return Right;
	}

	public char shrt() {
		if (this == Left)
			return 'L';
		return 'R';
	}
}
