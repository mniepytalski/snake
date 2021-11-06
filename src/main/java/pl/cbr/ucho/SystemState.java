package pl.cbr.ucho;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class SystemState {

    private int step;
}
