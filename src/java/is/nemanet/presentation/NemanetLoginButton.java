/**
 * 
 */
package is.nemanet.presentation;

import is.nemanet.business.NemanetEncryptionBean;

import java.io.IOException;

import javax.faces.context.FacesContext;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.PresentationObjectTransitional;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Parameter;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.encryption.RijndaelEncryptionBean;

/**
 * <p>
 * Button to post a form to the mentor web site and log the user into that
 * external webapplication.
 * </p>
 * Last modified: $Date$ by $Author$
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision$
 */
public class NemanetLoginButton extends PresentationObjectTransitional {

	private String webapplicationUrl = null;
	private String parameterKey1 = "keyvalue";
	private String target = "_new";

	public static final String IW_BUNDLE_IDENTIFIER = "is.nemanet";
	public static final String PROPERTY_SERVER_LOGIN_URL = "is.nemanet.loginurl";

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObject#getBundleIdentifier()
	 */
	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}

	/**
	 * @return Returns the webapplicationUrl.
	 */
	public String getWebapplicationUrl(IWMainApplication iwma) {
		if (this.webapplicationUrl == null) {
			String sPropLoginUrl = iwma.getSettings().getProperty(PROPERTY_SERVER_LOGIN_URL);
			return sPropLoginUrl;
		}
		else {
			return this.webapplicationUrl;
		}
	}

	/**
	 * @param webapplicationUrl
	 *          The webapplicationUrl to set.
	 */
	public void setWebapplicationUrl(String webapplicationUrl) {
		this.webapplicationUrl = webapplicationUrl;
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#initializeComponent(javax.faces.context.FacesContext)
	 */
	protected void initializeComponent(FacesContext context) {
		IWContext iwc = IWContext.getIWContext(context);
		IWMainApplication iwma = iwc.getIWMainApplication();

		Form form = new Form();
		form.setID("nemanetLoginButton");
		form.setStyleClass("nemanetForm");
		form.setAction(getWebapplicationUrl(iwma));
		form.setTarget(getTarget());

		Image image = new Image(getImageURI(iwc));
		SubmitButton button = new SubmitButton(image, "Submit");
		button.setStyleClass("nemanetButton");
		form.getChildren().add(button);

		String userPersonalId = getPersonalID(iwc);
		String encryptedPersonalId = encryptValue(iwma, userPersonalId);

		Parameter param2 = new Parameter(parameterKey1, encryptedPersonalId);
		form.getChildren().add(param2);

		getChildren().add(form);
	}

	/**
	 * <p>
	 * TODO tryggvil describe method getTarget
	 * </p>
	 * 
	 * @return
	 */
	private String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * <p>
	 * TODO tryggvil describe method encryptValue
	 * </p>
	 * 
	 * @param userPersonalId
	 * @return
	 */
	private String encryptValue(IWMainApplication iwma, String plaintext) {
		return getEncryptionBean(iwma).encrypt(plaintext);
	}

	public static RijndaelEncryptionBean getEncryptionBean(IWMainApplication iwma) {
		return NemanetEncryptionBean.getInstance(iwma);
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		super.encodeChildren(context);
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext arg0) throws IOException {
		super.encodeEnd(arg0);
	}

	protected String getPersonalID(IWContext iwc) {
		String userPersonalID = "";
		if (iwc.isLoggedOn()) {
			String personalID = iwc.getCurrentUser().getPersonalID();
			if (personalID != null && !personalID.equals("")) {
				userPersonalID = personalID;
			}
		}
		return userPersonalID;
	}

	protected String getImageURI(IWContext iwc) {
		IWBundle bundle = getBundle(iwc);
		return bundle.getResourcesURL() + "/nemanet.jpg";
	}

	/**
	 * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = this.target;
		values[2] = this.webapplicationUrl;
		return values;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(ctx, values[0]);
		this.target = (String) values[1];
		this.webapplicationUrl = (String) values[2];
	}

}
