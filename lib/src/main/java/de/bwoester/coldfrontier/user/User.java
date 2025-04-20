package de.bwoester.coldfrontier.user;

import de.bwoester.coldfrontier.data.Value;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class User {

    private final Value<UserMsg> userMsg;

}
