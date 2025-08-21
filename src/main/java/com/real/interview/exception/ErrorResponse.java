package com.real.interview.exception;

import java.time.LocalDate;

public record ErrorResponse(LocalDate localDate, String message, String detail) {
}
