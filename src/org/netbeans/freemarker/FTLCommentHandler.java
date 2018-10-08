package org.netbeans.freemarker;

import org.netbeans.modules.csl.spi.CommentHandler;

/**
 *
 * @author valc@ese.by
 */
public class FTLCommentHandler extends CommentHandler.DefaultCommentHandler {

    @Override
    public String getCommentStartDelimiter() {
        return "<#--";
    }

    @Override
    public String getCommentEndDelimiter() {
        return "-->";
    }

}
