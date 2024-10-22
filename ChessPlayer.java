public class ChessPlayer implements Comparable<ChessPlayer>{
    public final String name;
    public int elo;

    public ChessPlayer(String name){
        this.name = name;
        this.elo = 0;
    }

    public ChessPlayer(String name, int elo){
        this.name = name;
        this.elo = elo;
    }

    public int compareTo(ChessPlayer other){
        if(this.elo - other.elo == 0){
            return 0;
        }
        return (this.elo - other.elo) < 0 ? -1 : 1;
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(other == null){
            return false;
        }
        if(this.getClass() != other.getClass()){
            return false;
        }
        ChessPlayer cp = (ChessPlayer) other;
        return this.name.equals(cp.name)  && this.elo==cp.elo;
    }

    public int hashCode(){
        return name.hashCode();
    }

    public String toString(){
        return name + ": " + elo;
    }
}
