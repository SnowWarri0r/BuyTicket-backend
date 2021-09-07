package com.snowwarrior.huikuan.exception;

import com.snowwarrior.huikuan.constant.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.io.Serial;


public class AlreadyExistsException extends AbstractThrowableProblem {

    @Serial
    private static final long serialVersionUID = 4775907845387588528L;

    public AlreadyExistsException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.CONFLICT);
    }
}
