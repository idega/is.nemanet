/**
 * 
 */
package is.nemanet.business;

import com.idega.idegaweb.IWMainApplication;
import com.idega.util.encryption.RijndaelEncryptionBean;

/**
 * <p>
 * TODO tryggvil Describe Type MentorEncryptionBean
 * </p>
 * Last modified: $Date$ by $Author$
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision$
 */
public class NemanetEncryptionBean extends RijndaelEncryptionBean {

	public static final String BEAN_ID = "NemanetEncryptionBean";
	public static final String PROPERTY_ENCRYPTION_KEY = "is.nemanet.encryptionkey";
	public static final String PROPERTY_INIT_VECTOR = "is.nemanet.initvector";

	public static final NemanetEncryptionBean getInstance(IWMainApplication iwma) {
		NemanetEncryptionBean instance = (NemanetEncryptionBean) iwma.getAttribute(BEAN_ID);
		if (instance == null) {
			instance = new NemanetEncryptionBean();
			String key = iwma.getSettings().getProperty(PROPERTY_ENCRYPTION_KEY);
			String iv = iwma.getSettings().getProperty(PROPERTY_INIT_VECTOR);
			if (key != null && iv != null) {
				instance.setKeySize(16);
				instance.setSecretKey(key);
				instance.setIV(iv.getBytes());
			}
			iwma.setAttribute(BEAN_ID, instance);
		}
		return instance;
	}
}