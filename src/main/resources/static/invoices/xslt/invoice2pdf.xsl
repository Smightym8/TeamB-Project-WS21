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

					<!-- Logo -->
                    <fo:block text-align="right">
						<fo:external-graphic src="src/main/resources/static/invoices/assets/logo_black.png" content-height="20%"/>
					</fo:block>

					<!-- Guest Information -->
					<fo:block>
						<fo:block>
							<xsl:value-of select="invoice/guestFirstName"/>
							<xsl:text>&#x20;</xsl:text>
							<xsl:value-of select="invoice/guestLastName"/>
						</fo:block>

						<fo:block>
							<xsl:value-of select="invoice/streetName"/>
							<xsl:text>&#x20;</xsl:text>
							<xsl:value-of select="invoice/streetNumber"/>
						</fo:block>

						<fo:block>
							<xsl:value-of select="invoice/zipCode"/>
							<xsl:text>&#x20;</xsl:text>
							<xsl:value-of select="invoice/city"/>
						</fo:block>
					</fo:block>

					<!-- Rooms and prices -->
					<fo:block>
						<xsl:text>Rooms&#xd;</xsl:text>

						<fo:block>
							<fo:table>
								<fo:table-header border-width="1pt" border-style="solid">
									<fo:table-row font-weight="bold">
										<fo:table-cell>
											<fo:block>Quantity</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>Category</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>Cost per night</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-header>

								<fo:table-body border-width="1pt" border-style="solid">
									<xsl:for-each select="invoice/roomCategories">
										<fo:table-row>
											<fo:table-cell>
												<fo:block>
													<xsl:value-of select="entry/value" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>
													<xsl:value-of select="entry/key" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>
													<xsl:value-of select="roomCategoryPrice" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>

							</fo:table>
						</fo:block>

						<fo:block>
							<xsl:text>Booking period: From</xsl:text>
							<xsl:value-of select="invoice/checkInDate" />
							<xsl:text> to </xsl:text>
							<xsl:value-of select="invoice/checkOutDate" />
						</fo:block>

						<fo:block>
							<xsl:value-of select="invoice/amountOfNights" />
							<xsl:text> overnight stays</xsl:text>
						</fo:block>

						<fo:block>
							<xsl:text>Adults: </xsl:text>
							<xsl:value-of select="invoice/amountOfAdults" />
						</fo:block>

						<!--
						IF Abfrage für amountOfChildren
						-->

					</fo:block>

					<!-- Services and prices -->
					<fo:block>
						<xsl:text>Services&#xd;</xsl:text>

						<fo:block>
							<fo:table>
								<fo:table-header border-width="1pt" border-style="solid">
									<fo:table-row font-weight="bold">
										<fo:table-cell>
											<fo:block>Service</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>Cost</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-header>

								<fo:table-body border-width="1pt" border-style="solid">
									<xsl:for-each select="invoice/services">
										<fo:table-row>
											<fo:table-cell>
												<fo:block>
													<xsl:value-of select="entry/key" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>
													<xsl:value-of select="entry/value" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>

							</fo:table>
						</fo:block>
					</fo:block>

					<!-- Localtax -->
					<fo:block>
						<xsl:text>Further Services&#xd;</xsl:text>
						<fo:block>
							<fo:table>
								<fo:table-header border-width="1pt" border-style="solid">
									<fo:table-row font-weight="bold">
										<fo:table-cell>
											<fo:block>Quantity</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>Description</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>Cost per Adult</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>Total</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-header>

								<fo:table-body border-width="1pt" border-style="solid">
									<fo:table-row>
										<fo:table-cell>
											<fo:block>
												<xsl:value-of select="invoice/amountOfAdults" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>
												<xsl:text>Local Tax</xsl:text>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>
												<xsl:value-of select="invoice/localTaxPerPerson" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>
												<xsl:value-of select="invoice/localTaxTotal" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>

							</fo:table>
						</fo:block>
					</fo:block>

					<!-- VAT and total amount -->
					<fo:block>
						<xsl:text>Total</xsl:text>

						<fo:block>
							<xsl:text>Please indicate the invoice number as the purpose of payment.</xsl:text>
						</fo:block>

						<fo:block>
							<fo:table>
								<fo:table-header border-width="1pt" border-style="solid">
									<fo:table-row font-weight="bold">
										<fo:table-cell>
											<fo:block>Net Amount</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>VAT in €</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>VAT in %</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>Gross amount</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-header>

								<fo:table-body border-width="1pt" border-style="solid">
									<fo:table-row>
										<fo:table-cell>
											<fo:block>
												<xsl:value-of select="invoice/totalNetAmount" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>
												<xsl:value-of select="invoice/valueAddedTaxInEuro" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>
												<xsl:value-of select="invoice/valueAddedTaxInPercent" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block>
												<xsl:value-of select="invoice/totalGrossAmount" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</fo:block>

                </fo:flow>
                
			</fo:page-sequence>
			
		</fo:root>
	
	</xsl:template>
	
	
</xsl:stylesheet>