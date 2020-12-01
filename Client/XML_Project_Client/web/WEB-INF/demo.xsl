<?xml version="1.0" encoding="UTF-8"?>


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:param name="pathFile" select="'test'"/>
    <xsl:template match="EvaluateResult">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

            <fo:layout-master-set>
                <fo:simple-page-master master-name="x" page-height="8.5in" page-width="11in" margin-top="0.5in" margin-bottom="0.5in" margin-left="1in" margin-right="1in">
                    <fo:region-body margin-top="0.5in"/>
                    <fo:region-before extent="1in"/>
                    <fo:region-after extent=".75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="x">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="20pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        Flower information:
                    </fo:block>
                        
                    <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        - Flower name:
                        <xsl:value-of select="Flower/flowerName"/>
                    </fo:block>
                    <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        - Image:
                        <xsl:value-of select="Flower/imgLink"/>
                    </fo:block>
                    <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        - Temparature:
                        <xsl:value-of select="Flower/temparature"/>
                    </fo:block>
                    <fo:block font-size="20pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        State information:
                    </fo:block>
                    <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        - State name:
                        <xsl:value-of select="TempaState/stateName"/>
                    </fo:block>
                  
                    <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        - Season:
                        <xsl:value-of select="TempaState/season"/>
                    </fo:block>
                    <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        - Temparature:
                        <xsl:value-of select="TempaState/temparature"/>
                    </fo:block>
                    <fo:block font-size="20pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        Evaluation result:
                    </fo:block>
                    <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                        - <xsl:value-of select="Message"/>
                    </fo:block>
                    <xsl:if test="Check = 1">
                        <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="15pt"  padding-top="3pt" > 
                            <xsl:variable name="imgLink">
                                <xsl:value-of select="Bulb/imgLink"/>
                            </xsl:variable>
                            <fo:external-graphic src="url({$imgLink})" content-height="scale-to-fit" height="3.00in"/>
                        </fo:block>
                        <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                            + bulbName:
                            <xsl:value-of select="Bulb/bulbName"/>
                        </fo:block>
                        <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                            + quantity:
                            <xsl:value-of select="Bulb/Quantity"/>
                        </fo:block>
                        <fo:block font-size="14pt" font-family="Arial, Helvetica" line-height="24pt" space-after.optimum="10pt" text-align="left" padding-top="1pt"> 
                            + Image:
                            <xsl:value-of select="Bulb/imgLink"/>
                        </fo:block>
                    </xsl:if>
                </fo:flow>
                    
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>