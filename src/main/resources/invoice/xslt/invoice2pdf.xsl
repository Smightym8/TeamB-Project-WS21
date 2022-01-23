<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
				xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="/">
		<fo:root>

			<fo:layout-master-set>

				<fo:simple-page-master master-name="invoice"
									   page-height="297.0mm" page-width="209.9mm"
									   margin-bottom="8mm" margin-left="25mm" margin-right="10mm" margin-top="8mm">

					<fo:region-body margin-bottom="28mm" margin-left="0mm" margin-right="20mm" margin-top="20mm"/>


					<!-- region-after is the page footer -->
					<fo:region-after extent="24pt" region-name="hotel-footer"/>
				</fo:simple-page-master>

			</fo:layout-master-set>

			<fo:page-sequence master-reference="invoice">
				<!-- Footer info -->
				<fo:static-content flow-name="hotel-footer">
					<fo:block font-size="8pt">
						<xsl:element name="fo:table">
							<xsl:element name="fo:table-column">
								<xsl:attribute name="width">50mm</xsl:attribute>
							</xsl:element>
							<xsl:element name="fo:table-column">
								<xsl:attribute name="width">50mm</xsl:attribute>
							</xsl:element>
							<xsl:element name="fo:table-column">
								<xsl:attribute name="width">50mm</xsl:attribute>
							</xsl:element>
							<xsl:element name="fo:table-body">
								<xsl:element name="fo:table-row">
									<xsl:element name="fo:table-cell">
										<xsl:element name="fo:block">
											<xsl:text>Hotel Schwarz</xsl:text>
										</xsl:element>

										<xsl:element name="fo:block">
											<xsl:text>Thomas Schwarz</xsl:text>
										</xsl:element>

										<xsl:element name="fo:block">
											<xsl:text>Untermarktstraße 17</xsl:text>
										</xsl:element>

										<xsl:element name="fo:block">
											<xsl:text>6410 Telfs</xsl:text>
										</xsl:element>
									</xsl:element>
									<xsl:element name="fo:table-cell">
										<xsl:element name="fo:block">
											<xsl:text>Email: hotel@schwarz.com</xsl:text>
										</xsl:element>
										<xsl:element name="fo:block">
											<xsl:text>Phone: +43 5513 87506</xsl:text>
										</xsl:element>
									</xsl:element>
									<xsl:element name="fo:table-cell">
										<xsl:element name="fo:block">
											<xsl:text>BAWAG P.S.K</xsl:text>
										</xsl:element>
										<xsl:element name="fo:block">
											<xsl:text>IBAN: AT02 6000 0000 0134 9870</xsl:text>
										</xsl:element>
										<xsl:element name="fo:block">
											<xsl:text>BIC: OPSKATWW</xsl:text>
										</xsl:element>
									</xsl:element>
								</xsl:element>
							</xsl:element>
						</xsl:element>
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body" font-family="sans-serif" font-size="12pt">

					<!-- Logo -->
					<fo:block text-align="right">
						<fo:external-graphic src="classpath:/invoice/assets/logo_black.png" content-height="20%"/>
					</fo:block>

					<!-- Guest Information -->
					<fo:block font-size="10pt" margin-top="10mm">
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

					<!-- Invoice Details -->
					<fo:block text-align="right" margin-top="10pt" font-size="10pt">
						<fo:block>
							<xsl:text>Invoice number: </xsl:text>
							<xsl:value-of select="invoice/invoiceNumber" />
						</fo:block>

						<fo:block>
							<xsl:text>Invoice date: </xsl:text>
							<xsl:value-of select="invoice/invoiceDate" />
						</fo:block>
					</fo:block>

					<!-- Rooms and prices -->
					<fo:block margin-top="10mm">
						<fo:inline font-size="12pt" font-weight="bold">
							<xsl:text>Rooms&#xd;</xsl:text>
						</fo:inline>

						<fo:block margin-top="1mm">
							<fo:table font-size="10pt">
								<fo:table-column column-width="30%"/>
								<fo:table-column column-width="30%"/>
								<fo:table-column column-width="30%"/>
								<fo:table-column column-width="10%"/>

								<fo:table-header border-width="1pt" border-style="solid">
									<fo:table-row font-weight="bold">
										<fo:table-cell padding="3pt">
											<fo:block>Name</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="3pt">
											<fo:block>Category</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="3pt">
											<fo:block>Cost per night</fo:block>
										</fo:table-cell>
										<fo:table-cell text-align="right" padding="3pt">
											<fo:block>Total</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-header>

								<fo:table-body border-width="1pt" border-style="solid">
									<xsl:for-each select="/invoice/roomNames">
										<xsl:variable name ="pos" select="position()" />
										<fo:table-row>
											<fo:table-cell padding="3pt">
												<fo:block>
													<xsl:value-of select="/invoice/roomNames[$pos]" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell padding="3pt">
												<fo:block>
													<xsl:value-of select="/invoice/categoryNames[$pos]" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell padding="3pt">
												<fo:block>
													<xsl:text>€ </xsl:text>
													<xsl:value-of select="/invoice/categoryPrices[$pos]" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell text-align="right" padding="3pt">
												<fo:block>
													<xsl:text>€ </xsl:text>
													<xsl:value-of select="format-number((/invoice/categoryPrices[$pos] * number(/invoice/amountOfNights)), '#.00')" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:block>

						<fo:block margin-top="2mm" font-size="10pt">
							<xsl:text>Booking period: From </xsl:text>
							<xsl:value-of select="invoice/checkInDate" />
							<xsl:text> to </xsl:text>
							<xsl:value-of select="invoice/checkOutDate" />
						</fo:block>

						<fo:block font-size="10pt">
							<xsl:value-of select="invoice/amountOfNights" />
							<xsl:text> overnight stays</xsl:text>
						</fo:block>

						<fo:block font-size="10pt">
							<xsl:text>Adults: </xsl:text>
							<xsl:value-of select="invoice/amountOfAdults" />
						</fo:block>

						<xsl:if test="invoice/amountOfChildren > 0">
							<fo:block font-size="10pt">
								<xsl:text>Children: </xsl:text>
								<xsl:value-of select="invoice/amountOfChildren" />
							</fo:block>
						</xsl:if>
					</fo:block>

					<!-- Services and prices -->
					<xsl:if test="invoice/services/entry">
						<fo:block margin-top="8mm">
							<fo:inline font-size="12pt" font-weight="bold">
								<xsl:text>Services&#xd;</xsl:text>
							</fo:inline>

							<fo:block margin-top="1mm">
								<fo:table font-size="10pt">
									<fo:table-column column-width="30%"/>
									<fo:table-column column-width="30%"/>
									<fo:table-column column-width="30%"/>
									<fo:table-column column-width="10%"/>

									<fo:table-header border-width="1pt" border-style="solid">
										<fo:table-row font-weight="bold"  padding="3pt">
											<fo:table-cell padding="3pt">
												<fo:block>Quantity</fo:block>
											</fo:table-cell>
											<fo:table-cell padding="3pt">
												<fo:block>Service</fo:block>
											</fo:table-cell>
											<fo:table-cell padding="3pt">
												<fo:block>Cost per night</fo:block>
											</fo:table-cell>
											<fo:table-cell text-align="right" padding="3pt">
												<fo:block>Total</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-header>

									<fo:table-body border-width="1pt" border-style="solid">
										<xsl:for-each select="invoice/services/entry">
											<fo:table-row>
												<fo:table-cell padding="3pt">
													<fo:block>
														<xsl:value-of select="count(/invoice/roomNames)"/>
													</fo:block>
												</fo:table-cell>
												<fo:table-cell padding="3pt">
													<fo:block>
														<xsl:value-of select="key" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell padding="3pt">
													<fo:block>
														<xsl:text>€ </xsl:text>
														<xsl:value-of select="value" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="right" padding="3pt">
													<fo:block>
														<xsl:text>€ </xsl:text>
														<xsl:value-of select="format-number((value * count(/invoice/roomNames) * number(/invoice/amountOfNights)), '#.00')" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</xsl:for-each>
									</fo:table-body>
								</fo:table>
							</fo:block>
						</fo:block>
					</xsl:if>

					<!-- Localtax -->
					<xsl:if test="(invoice/localTaxTotal) > 0">
						<fo:block margin-top="8mm">
							<fo:inline font-size="12pt" font-weight="bold">
								<xsl:text>Further Services&#xd;</xsl:text>
							</fo:inline>

							<fo:block margin-top="1mm">
								<fo:table font-size="10pt">
									<fo:table-column column-width="30%"/>
									<fo:table-column column-width="30%"/>
									<fo:table-column column-width="30%"/>
									<fo:table-column column-width="10%"/>

									<fo:table-header border-width="1pt" border-style="solid">
										<fo:table-row font-weight="bold">
											<fo:table-cell padding="3pt">
												<fo:block>Quantity</fo:block>
											</fo:table-cell>
											<fo:table-cell padding="3pt">
												<fo:block>Description</fo:block>
											</fo:table-cell>
											<fo:table-cell padding="3pt">
												<fo:block>Cost per Adult</fo:block>
											</fo:table-cell>
											<fo:table-cell text-align="right" padding="3pt">
												<fo:block>Total</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-header>

									<fo:table-body border-width="1pt" border-style="solid">
										<fo:table-row>
											<fo:table-cell padding="3pt">
												<fo:block>
													<xsl:value-of select="invoice/amountOfAdults" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell padding="3pt">
												<fo:block>
													<xsl:text>Local Tax</xsl:text>
												</fo:block>
											</fo:table-cell>
											<fo:table-cell padding="3pt">
												<fo:block>
													<xsl:text>€ </xsl:text>
													<xsl:value-of select="invoice/localTaxPerPerson" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell text-align="right" padding="3pt">
												<fo:block>
													<xsl:text>€ </xsl:text>
													<xsl:value-of select="invoice/localTaxTotal" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>

								</fo:table>
							</fo:block>
						</fo:block>
					</xsl:if>

					<!-- VAT and total amount -->
					<fo:block margin-top="8mm">
						<fo:inline font-size="12pt" font-weight="bold">
							<xsl:text>Total</xsl:text>
						</fo:inline>

						<fo:block margin-top="1mm">

							<fo:table font-size="10pt">
								<fo:table-body>

									<fo:table-row border-width="1pt" border-style="solid" font-weight="bold">
										<fo:table-cell padding="3pt">
											<fo:block>Net Amount</fo:block>
										</fo:table-cell>
										<fo:table-cell text-align="right" padding="3pt">
											<fo:block>
												<xsl:text>€ </xsl:text>
												<xsl:value-of select="invoice/totalNetAmountBeforeDiscount" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<xsl:if test="(invoice/discountInPercent) > 0">
										<fo:table-row border-width="1pt" border-style="solid">
											<fo:table-cell padding="3pt">
												<fo:block>
													<xsl:text>Discount (</xsl:text>
													<xsl:value-of select="(invoice/discountInPercent)" />
													<xsl:text>% )</xsl:text>
												</fo:block>
											</fo:table-cell>
											<fo:table-cell text-align="right" padding="3pt">
												<fo:block>
													<xsl:text>- € </xsl:text>
													<xsl:value-of select="(invoice/discountInEuro)" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>

									<fo:table-row border-width="1pt" border-style="solid" font-weight="bold">
										<fo:table-cell padding="3pt">
											<fo:block>Net Amount after discount</fo:block>
										</fo:table-cell>
										<fo:table-cell text-align="right" padding="3pt">
											<fo:block>
												<xsl:text>€ </xsl:text>
												<xsl:value-of select="invoice/totalNetAmountAfterDiscount" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<fo:table-row border-width="1pt" border-style="solid">
										<fo:table-cell padding="3pt">
											<fo:block>
												<xsl:text>VAT (</xsl:text>
												<xsl:value-of select="(invoice/valueAddedTaxInPercent) * 100" />
												<xsl:text>.0 % )</xsl:text>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell text-align="right" padding="3pt">
											<fo:block>
												<xsl:text>€ </xsl:text>
												<xsl:value-of select="invoice/valueAddedTaxInEuro" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>

									<xsl:if test="(invoice/localTaxTotal) > 0">
										<fo:table-row border-width="1pt" border-style="solid">
											<fo:table-cell padding="3pt">
												<fo:block>
													<xsl:text>Local Tax</xsl:text>
												</fo:block>
											</fo:table-cell>
											<fo:table-cell text-align="right" padding="3pt">
												<fo:block>
													<xsl:text>€ </xsl:text>
													<xsl:value-of select="invoice/localTaxTotal" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>

									<fo:table-row border-width="1pt" border-style="solid" font-weight="bold">
										<fo:table-cell padding="3pt">
											<fo:block>Gross amount</fo:block>
										</fo:table-cell>
										<fo:table-cell text-align="right" padding="3pt">
											<fo:block>
												<xsl:text>€ </xsl:text>
												<xsl:value-of select="invoice/totalGrossAmount" />
											</fo:block>
										</fo:table-cell>
									</fo:table-row>

								</fo:table-body>
							</fo:table>

						</fo:block>

						<fo:block margin-top="3mm" font-size="10pt">
							<xsl:text>Please indicate the invoice number as the purpose of payment.</xsl:text>
						</fo:block>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>

		</fo:root>

	</xsl:template>


</xsl:stylesheet>