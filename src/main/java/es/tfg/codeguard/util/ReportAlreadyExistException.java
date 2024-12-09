package es.tfg.codeguard.util;

public class ReportAlreadyExistException extends RuntimeException{
	public ReportAlreadyExistException(String message) {
		super(message);
	}
}
