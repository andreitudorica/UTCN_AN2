package com.company;

public class Ticket {
    private final int TicketID;
    private Destination dest;
    private Shuttle shuttle;
    private Seat seat;

    public Destination getDest() {
        return dest;
    }
    public Ticket(Destination dest,Station start){
        TicketID=(int) Math.random()*1000000000;
        this.dest=dest;
        for(Shuttle sh:start.getShuttles()) {
            int destIndex=0;
            boolean found=false;
            for(Destination d:sh.getRoute().getDstinations())
            {
                destIndex++;
                if(d.getId()==dest.getId()) {
                    found = true;
                    break;
                }
            }
            if(found)
            {
                if(sh.getSeatsCount()>0)
                {
                    sh.setSeatsCount(sh.getSeatsCount()-1);
                    Seat s=new Seat();
                    sh.addSeat(s, destIndex*4.5);
                    this.shuttle=sh;
                    this.seat=s;
                    break;
                }
            }
        }
    }
    public int getTicketID() {
        return TicketID;
    }

    public Shuttle getShuttle() {
        return shuttle;
    }

}
