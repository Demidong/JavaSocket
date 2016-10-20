package SocketImpl;

/**
 * Created by demi on 16/10/19.
 */
public class SocketBean {
    public int cmd ;
    public String seq ;
    public String content ;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "cmd ："+ cmd + "seq ："+ seq + "content ：" + content;
    }
}
