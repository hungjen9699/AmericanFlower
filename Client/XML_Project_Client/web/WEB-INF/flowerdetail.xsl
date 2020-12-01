<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : flowerdetail.xsl
    Created on : November 7, 2020, 8:38 PM
    Author     : hungj
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="flower">
 
                <h2>
                    <xsl:value-of select="flowerName"/>
                </h2>
                <p class="available-stock">
                    <span> Temparature : <a href="#"> <xsl:value-of select="temparature"/> celcius</a></span>
                </p>
                <p class="available-stock">
                    <span> Season : <a href="#">  <xsl:value-of select="season"/></a></span>
                </p>
                <p class="available-stock">
                    <span> Maturity day: <a href="#"> <xsl:value-of select="maturity"/> days</a></span>
                </p>    
                 <p class="available-stock">
                    <span> Germination day: <a href="#"> <xsl:value-of select="germination"/> days</a></span>
                </p> 
             
    </xsl:template>
</xsl:stylesheet>
