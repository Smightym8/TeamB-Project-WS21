<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format">
    
	<xsl:template match="/">
		<fo:root>
		
			<fo:layout-master-set>
			
				<fo:simple-page-master master-name="invoice"
					page-height="297.0mm" page-width="209.9mm"
                    margin-bottom="8mm" margin-left="25mm" margin-right="10mm" margin-top="10mm">
                    
                    <fo:region-body margin-bottom="28mm" margin-left="0mm" margin-right="44mm" margin-top="20mm"/>
                    
				</fo:simple-page-master>
				
			</fo:layout-master-set>
			
			<fo:page-sequence master-reference="invoice">
			
				<fo:flow flow-name="xsl-region-body" font-family="sans-serif" font-size="14pt">
                    <fo:block>
						<xsl:value-of select="invoice/guestFirstName"/> <xsl:value-of select="invoice/guestLastName"/>
                    </fo:block>
                </fo:flow>
                
			</fo:page-sequence>
			
		</fo:root>
	
	</xsl:template>
	
	
</xsl:stylesheet>