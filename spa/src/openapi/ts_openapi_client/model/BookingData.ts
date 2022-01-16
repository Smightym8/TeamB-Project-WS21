export interface BookingData {
    firstName?: string,
    lastName?: string,
    gender?: string,
    eMail?: string,
    phoneNumber?: string,
    birthDate?: string,
    streetName?: string,
    streetNumber?: string,
    zipCode?: string,
    city?: string,
    country?: string,
    roomCategoryIds?: string[],
    roomCategoryAmounts?: number[],
    serviceIds?: string[],
    checkInDate?: string,
    checkOutDate?: string,
    amountOfAdults?: number,
    amountOfChildren?: number
    additionalInformation?: string
}