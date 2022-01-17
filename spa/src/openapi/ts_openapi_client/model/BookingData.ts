export interface BookingData {
    firstName: string,
    lastName: string,
    gender: string,
    eMail: string,
    phoneNumber: string,
    birthDate: string,
    streetName: string,
    streetNumber: string,
    zipCode: string,
    city: string,
    country: string,
    finalRoomCategoryIds: string[],
    finalRoomCategoryAmounts: number[],
    finalServiceIds: string[],
    checkInDate: string,
    checkOutDate: string,
    amountOfAdults: number,
    amountOfChildren: number
    additionalInformation: string
}