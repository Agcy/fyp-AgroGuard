export interface Post{
  id: string;
  title: string;
  base64Image: string;
  content: string;
  userId: string;
  userName: string;
  updatedAt: string;
}

export interface Detection {
  id: string;
  userId: string;
  sourceImageUrl: string;
  detectionResult: string;
  gmtDetected: string;
}
