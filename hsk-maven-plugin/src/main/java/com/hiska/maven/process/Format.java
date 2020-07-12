/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.bcb.maven.process;

/**
 *
 * @author yracnet
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Format {

    private String skipCss = "false";
    private String skipHtml = "false";
    private String skipJava = "false";
    private String skipJs = "false";
    private String skipJson = "false";
    private String skipXml = "false";

    public boolean isSkipXml() {
        return Boolean.valueOf(skipXml);
    }

    public boolean isSkipHtml() {
        return Boolean.valueOf(skipHtml);
    }

}
