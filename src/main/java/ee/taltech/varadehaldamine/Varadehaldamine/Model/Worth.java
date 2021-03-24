package ee.taltech.varadehaldamine.Varadehaldamine.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Worth {
    @Id
    private String asset_id;
    private Double price;
    private Double residual_price;
    private Timestamp purchase_date;
}
