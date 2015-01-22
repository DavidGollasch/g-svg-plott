package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainWindow {
	
	public static final String APP_NAME = "gSVGPlott";

	protected Shell shlGsvgplott;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		
		createContents();
		shlGsvgplott.open();
		shlGsvgplott.layout();
		while (!shlGsvgplott.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlGsvgplott = new Shell();
		shlGsvgplott.setSize(793, 502);
		shlGsvgplott.setText(MainWindow.APP_NAME);
		
		Button btnClose = new Button(shlGsvgplott, SWT.NONE);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				MessageBox messageBox = new MessageBox(shlGsvgplott, SWT.ICON_QUESTION |SWT.YES | SWT.NO);
			    messageBox.setMessage("Are you sure that you want to close this application?");
			    int rc = messageBox.open();
			    
			    switch (rc) {
				case SWT.YES:
					shlGsvgplott.close();
					break;
				case SWT.NO:
					break;
				default: //do nothing
					break;
				}
			}
		});
		btnClose.setBounds(10, 10, 95, 28);
		btnClose.setText("Close");

	}
}
