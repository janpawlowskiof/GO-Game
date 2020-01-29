package juanolek.DBconnectors;

import javax.persistence.*;

@Entity
@Table(name = "moves")
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    int ID;
    @Column(name = "wsp_x")
    int x;
    @Column(name = "wsp_y")
    int y;
    @Column(name = "color")
    String color;
    @Column(name = "Id_gry")
    String gameId;

    public Move(int x, int y, String color,String gameId){
        this.x = x;
        this.color=color;
        this.y=y;
        this.gameId = gameId;
    }

    public Move(){

    }


}
