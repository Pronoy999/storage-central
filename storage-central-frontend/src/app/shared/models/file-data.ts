export interface FileDataJson {
    id?: string,
    userId: string,
    fileLastModified: number,
    fileLastModifiedDate: Date,
    fileName: string,
    fileSize: number,
    fileType: string,
    encodedData: string,
    uploadedDate: Date
}