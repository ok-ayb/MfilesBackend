package io.xhub.smwall.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Payload {
    private Action action;
    private Object data;
}
