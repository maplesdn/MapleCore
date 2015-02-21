/**
 * Created by zhushigang on 2/21/15.
 */
class MapleSystem{
    Controller C;
    void init(Controller c){
        this.C = c;

    }

    void handlePacket(int p){
        C.sendPacket(p+1);
    }

}
