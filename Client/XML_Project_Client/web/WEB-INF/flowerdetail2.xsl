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
        <img class="d-block w-100" alt="First slide" style="height:520px;">
            <xsl:attribute name="src">
                <xsl:value-of select="imgLink"/>
            </xsl:attribute>
        </img>
    </xsl:template>
</xsl:stylesheet>
