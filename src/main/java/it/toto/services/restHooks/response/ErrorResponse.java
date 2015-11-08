package it.toto.services.restHooks.response;

import lombok.*;
import org.apache.commons.lang.StringUtils;

/**
 * Created by toto on 07/10/14.
 */


@NoArgsConstructor (access = AccessLevel.PRIVATE)
@ToString
public class ErrorResponse {

    /**
     * Created by toto on 09/10/14.
     */
    public enum  DetailsCode {
        EXCEPTION, QUARTZ, JOB_TYPE_UNDEFINED, INVALID_CREDENTIALS, AUTHENTICATION_REQUIRED, PROFILE_NOT_ADMIN, INVALID_PROFILE, PROFILE_NOT_CUSTOMER;
    }

    @Getter
    @ToString
    static class CodeMessage {

        @NonNull
        private String code;
        @NonNull
        private String message;

        public CodeMessage(Error error) {
            this.code = error.name();
            this.message = error.getMessage();
        }
    }

    private CodeMessage main;

    public String getCode() {
        return main.getCode();
    }

    public String getMessage() {
        return main.getMessage();
    }

    @Getter
    private CodeMessage[] errors;

    public static ErrorResponse of(Error main, Error... subErrors) {
        ErrorResponse result = new ErrorResponse();
        result.main = new CodeMessage(main);
        result.errors = new CodeMessage[subErrors.length];
        for (int i = 0; i < subErrors.length; i++) {
            result.errors[i] = new CodeMessage(subErrors[i]);
        }

        return result;
    }

    /**
     * Created by toto on 07/10/14.
     */
    public static interface Error {

        public String name();
        public String getMessage();

    }


    public static ErrorResponse of(final Throwable e) {
        return ErrorResponse.of(new Error() {
            @Override
            public String name() {
                return DetailsCode.EXCEPTION.name();
            }

            @Override
            public String getMessage() {
                return e.getMessage();
            }
        });
    }


    public static ErrorResponse __star(final DetailsCode detail) {
        return ErrorResponse.of(new Error() {
            @Override
            public String name() {
                return "*";
            }

            @Override
            public String getMessage() {
                return detail.name();
            }
        });
    }

    public static ErrorResponse onlyCode(final DetailsCode errorDetailsCode) {
        return ErrorResponse.of(new Error() {
            @Override
            public String name() {
                return errorDetailsCode.name();
            }

            @Override
            public String getMessage() {
                return StringUtils.EMPTY;
            }
        });
    }

    public static ErrorResponse of(final DetailsCode code, final DetailsCode details) {
        return ErrorResponse.of(new Error() {
            @Override
            public String name() {
                return code.name();
            }

            @Override
            public String getMessage() {
                return details.name();
            }
        });
    }

}
