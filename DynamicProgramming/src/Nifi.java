import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class Nifi {

	public static void main(String args[]) throws IOException {
		Regions clientRegion = Regions.US_EAST_1;
		String bucketName = "9506-nessus-test";
		String key = "updated_nessus.csv";

		S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion)
					.withCredentials(new ProfileCredentialsProvider()).build();
			System.out.println("Downloading an object");
			fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
			System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
			System.out.println("Content: ");
			displayTextInputStream(fullObject.getObjectContent());
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		} finally {
			// To ensure that the network connection doesn't remain open, close any open
			// input streams.
			if (fullObject != null) {
				fullObject.close();
			}
			if (objectPortion != null) {
				objectPortion.close();
			}
			if (headerOverrideObject != null) {
				headerOverrideObject.close();
			}
		}
	}

	private static void displayTextInputStream(InputStream input) throws IOException {
		// Read the text input stream one line at a time and display each line.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		System.out.println();
	}

}
