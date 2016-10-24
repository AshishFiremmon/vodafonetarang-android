package Modal;


/**
 * Created by firemoonpc_11 on 12-07-2016.
 */
public class ResolveDetalModal {


    public String getTicket_no() {
        return ticket_no;
    }

    public String getOrigin_date() {
        return origin_date;
    }

    public String getResolve_date() {
        return resolve_date;
    }

    public String getTicket_summery() {
        return ticket_summery;
    }

    public String getMobile() {
        return mobile;
    }

    public String getZone() {
        return zone;
    }

    String ticket_no;
    String origin_date;
    String resolve_date;
    String ticket_summery;
    String mobile;
    String zone;

    public String getMark_as_resolved() {
        return mark_as_resolved;
    }

    String mark_as_resolved;

    public String getExp_clouser_date() {
        return exp_clouser_date;
    }

    String exp_clouser_date;


 public ResolveDetalModal(String ticket_no, String origin_date, String resolve_date, String ticket_summery,String mobile, String zone,String mark_as_resolved, String exp_clouser_date)
    {
        this.ticket_no=ticket_no;
        this.origin_date=origin_date;
        this.resolve_date=resolve_date;
        this.ticket_summery=ticket_summery;
        this.mobile=mobile;
        this.zone=zone;
        this.mark_as_resolved=mark_as_resolved;
        this.exp_clouser_date=exp_clouser_date;

    }


}
