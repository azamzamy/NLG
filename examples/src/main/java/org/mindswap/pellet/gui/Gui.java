							
							/*
							 * Copyright (c) 2010, Oracle. All rights reserved.
							 *
							 * Redistribution and use in source and binary forms, with or without
							 * modification, are permitted provided that the following conditions are met:
							 *
							 * * Redistributions of source code must retain the above copyright notice,
							 *   this list of conditions and the following disclaimer.
							 *
							 * * Redistributions in binary form must reproduce the above copyright notice,
							 *   this list of conditions and the following disclaimer in the documentation
							 *   and/or other materials provided with the distribution.
							 *
							 * * Neither the name of Oracle nor the names of its contributors
							 *   may be used to endorse or promote products derived from this software without
							 *   specific prior written permission.
							 *
							 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
							 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
							 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
							 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
							 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
							 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
							 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
							 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
							 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
							 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
							 * THE POSSIBILITY OF SUCH DAMAGE.
							 */
							
							package org.mindswap.pellet.gui;
							
							import java.io.File;
							import java.io.FileReader;
							import java.io.IOException;
							import java.util.ArrayList;
							import java.util.List;
							import java.util.Set;
							
							import javax.swing.JFileChooser;
							import javax.swing.JOptionPane;
							
							import org.mindswap.pellet.Bachelor.Wine;
							import org.semanticweb.owlapi.model.OWLException;
							import org.semanticweb.owlapi.model.OWLIndividual;
							import org.semanticweb.owlapi.model.OWLOntologyCreationException;
							
							import org.apache.xerces.util.SynchronizedSymbolTable;
							import org.semanticweb.owlapi.apibinding.OWLManager;
							import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
							import org.semanticweb.owlapi.model.IRI;
							import org.semanticweb.owlapi.model.OWLAnnotation;
							import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
							import org.semanticweb.owlapi.model.OWLAnnotationProperty;
							import org.semanticweb.owlapi.model.OWLAxiom;
							import org.semanticweb.owlapi.model.OWLClass;
							import org.semanticweb.owlapi.model.OWLDataFactory;
							import org.semanticweb.owlapi.model.OWLEntity;
							import org.semanticweb.owlapi.model.OWLException;
							import org.semanticweb.owlapi.model.OWLLiteral;
							import org.semanticweb.owlapi.model.OWLNamedIndividual;
							import org.semanticweb.owlapi.model.OWLObject;
							import org.semanticweb.owlapi.model.OWLObjectProperty;
							import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
							import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
							import org.semanticweb.owlapi.model.OWLOntology;
							import org.semanticweb.owlapi.model.OWLOntologyCreationException;
							import org.semanticweb.owlapi.model.OWLOntologyManager;
							import org.semanticweb.owlapi.model.OWLOntologyStorageException;
							import org.semanticweb.owlapi.model.PrefixManager;
							import org.semanticweb.owlapi.reasoner.Node;
							import org.semanticweb.owlapi.reasoner.NodeSet;
							import org.semanticweb.owlapi.util.DefaultPrefixManager;
							import org.semanticweb.owlapi.util.InferredAxiomGenerator;
							import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
							import org.semanticweb.owlapi.util.InferredOntologyGenerator;
							import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
							import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;
							
							import com.clarkparsia.owlapi.explanation.PelletExplanation;
							import com.clarkparsia.owlapi.explanation.io.manchester.ManchesterSyntaxExplanationRenderer;
							import com.clarkparsia.owlapiv3.OWL;
							import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
							import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
							import com.hp.hpl.jena.ontology.ObjectProperty;
							import com.hp.hpl.jena.ontology.OntClass;
							
							public class Gui extends javax.swing.JFrame {
							
								Wine wine;
							
								public Gui() {
									initComponents();
								}
							
								/**
								 * This method is called from within the constructor to initialize the form.
								 * WARNING: Do NOT modify this code. The content of this method is always
								 * regenerated by the Form Editor.
								 */
								// <editor-fold defaultstate="collapsed" desc="Generated Code">
								private void initComponents() {
							
									buttonGroup1 = new javax.swing.ButtonGroup();
									jPanel1 = new javax.swing.JPanel();
									jLabel1 = new javax.swing.JLabel();
									jLabel3 = new javax.swing.JLabel();
									label1 = new javax.swing.JLabel();
									box1 = new javax.swing.JComboBox();
									jButton7 = new javax.swing.JButton();
									fname = new javax.swing.JLabel();
									label2 = new javax.swing.JLabel();
									box2 = new javax.swing.JComboBox<ClassObj>();
									label3 = new javax.swing.JLabel();
									box3 = new javax.swing.JComboBox<IndObj>();
									label4 = new javax.swing.JLabel();
									box4 = new javax.swing.JComboBox<PropertyObj>();
									jButton8 = new javax.swing.JButton();
									jLabel2 = new javax.swing.JLabel();
									label5 = new javax.swing.JLabel();
									box5 = new javax.swing.JComboBox<IndObj>();
									jPanel2 = new javax.swing.JPanel();
									jLabel6 = new javax.swing.JLabel();
							
									explanationRes = new javax.swing.JLabel();
									exitButton = new javax.swing.JButton();
									jScrollPane1 = new javax.swing.JScrollPane();
									jTextPane1 = new javax.swing.JTextPane();
									setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
									setTitle("NLG Explanation");
									setBackground(new java.awt.Color(204, 204, 255));
							
									jPanel1.setBackground(new java.awt.Color(204, 204, 255));
									jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(" Input "));
							
									jLabel1.setText("File name:");
							
									jLabel3.setText("Selected File:");
							
									label1.setText("Choose Type of Explanation:");
							
									box1.setModel(new javax.swing.DefaultComboBoxModel(
											new String[] { "Type of Explanation", "Entailment Explanation", "Inconsistency Explanation",
													"Instance Explanation", "Subclass Explanation", "Unsatisfiable Explanation" }));
									box1.setSelectedIndex(0);
									box1.setEnabled(false);
									box1.addActionListener(new java.awt.event.ActionListener() {
										public void actionPerformed(java.awt.event.ActionEvent evt) {
											box1ActionPerformed(evt);
										}
									});
							
									jButton7.setText("Browse");
									jButton7.addActionListener(new java.awt.event.ActionListener() {
										public void actionPerformed(java.awt.event.ActionEvent evt) {
											try {
												jButton7ActionPerformed(evt);
											} catch (OWLException | IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									});
							
									fname.setText("No File");
							
									label2.setText("Choose Class:");
							
									box2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Classes" }));
									box2.setEnabled(false);
									box2.addActionListener(new java.awt.event.ActionListener() {
										public void actionPerformed(java.awt.event.ActionEvent evt) {
											box2ActionPerformed(evt);
										}
									});
							
									label3.setText("Choose Instance: ");
							
									box3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Instances" }));
									box3.setEnabled(false);
									box3.addActionListener(new java.awt.event.ActionListener() {
										public void actionPerformed(java.awt.event.ActionEvent evt) {
											box3ActionPerformed(evt);
										}
									});
							
									label4.setText("Choose Choose Object Property:");
							
									box4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Object properties" }));
									box4.setEnabled(false);
									box4.addActionListener(new java.awt.event.ActionListener() {
										public void actionPerformed(java.awt.event.ActionEvent evt) {
											try {
												box4ActionPerformed(evt);
											} catch (OWLOntologyCreationException | InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									});
							
									jButton8.setText("Activate Reasoner");
									jButton8.setEnabled(false);
									jButton8.addActionListener(new java.awt.event.ActionListener() {
										public void actionPerformed(java.awt.event.ActionEvent evt) {
											try {
												jButton8ActionPerformed(evt);
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (OWLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									});
							
									jLabel2.setForeground(new java.awt.Color(102, 102, 102));
									jLabel2.setText("Please select .owl file");
							
									label5.setText("Choose Corresponding instance:");
							
									box5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Instances" }));
									box5.setEnabled(false);
									box5.addActionListener(new java.awt.event.ActionListener() {
										public void actionPerformed(java.awt.event.ActionEvent evt) {
											box5ActionPerformed(evt);
										}
									});
							
									org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
							        jPanel1.setLayout(jPanel1Layout);
							        jPanel1Layout.setHorizontalGroup(
							            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							            .add(jPanel1Layout.createSequentialGroup()
							                .addContainerGap()
							                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                    .add(jPanel1Layout.createSequentialGroup()
							                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                            .add(jLabel1)
							                            .add(label5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 211, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
							                        .add(88, 721, Short.MAX_VALUE))
							                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
							                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
							                            .add(jPanel1Layout.createSequentialGroup()
							                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                                    .add(jPanel1Layout.createSequentialGroup()
							                                        .add(label1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							                                        .add(32, 32, 32))
							                                    .add(jPanel1Layout.createSequentialGroup()
							                                        .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
							                                .add(40, 40, 40)
							                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                                    .add(jPanel1Layout.createSequentialGroup()
							                                        .add(6, 6, 6)
							                                        .add(fname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 323, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
							                                    .add(box1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 573, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                                    .add(jPanel1Layout.createSequentialGroup()
							                                        .add(jButton7)
							                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							                                        .add(jLabel2))))
							                            .add(jPanel1Layout.createSequentialGroup()
							                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                                    .add(label3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 202, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                                    .add(label4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 211, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                                    .add(label2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 219, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
							                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                                    .add(jButton8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 212, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
							                                        .add(org.jdesktop.layout.GroupLayout.TRAILING, box3, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							                                        .add(org.jdesktop.layout.GroupLayout.TRAILING, box2, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							                                        .add(box4, 0, 573, Short.MAX_VALUE)
							                                        .add(box5, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
							                        .add(85, 85, 85))))
							        );
							        jPanel1Layout.setVerticalGroup(
							            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							            .add(jPanel1Layout.createSequentialGroup()
							                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
							                    .add(jLabel1)
							                    .add(jButton7)
							                    .add(jLabel2))
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
							                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
							                    .add(jLabel3)
							                    .add(fname))
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
							                    .add(box1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                    .add(label1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
							                    .add(box2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                    .add(label2))
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
							                    .add(box3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                    .add(label3))
							                .add(10, 10, 10)
							                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                    .add(box4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                    .add(label4))
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
							                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                    .add(box5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                    .add(label5))
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							                .add(jButton8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                .add(0, 6, Short.MAX_VALUE))
							        );

							        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
							        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(" Output "));

							        jLabel6.setText("Explanation For: ");

							        jScrollPane1.setViewportView(jTextPane1);

							        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
							        jPanel2.setLayout(jPanel2Layout);
							        jPanel2Layout.setHorizontalGroup(
							            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							            .add(jPanel2Layout.createSequentialGroup()
							                .addContainerGap()
							                .add(jLabel6)
							                .add(209, 209, 209)
							                .add(explanationRes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 460, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
							                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 906, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                .addContainerGap())
							        );
							        jPanel2Layout.setVerticalGroup(
							            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							            .add(jPanel2Layout.createSequentialGroup()
							                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                    .add(explanationRes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                    .add(jPanel2Layout.createSequentialGroup()
							                        .addContainerGap()
							                        .add(jLabel6)))
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
							                .addContainerGap())
							        );

							        exitButton.setText("Done");
							        exitButton.addActionListener(new java.awt.event.ActionListener() {
							            public void actionPerformed(java.awt.event.ActionEvent evt) {
							                exitButtonActionPerformed(evt);
							            }
							        });

							        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
							        getContentPane().setLayout(layout);
							        layout.setHorizontalGroup(
							            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							            .add(layout.createSequentialGroup()
							                .addContainerGap()
							                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
							                        .add(0, 0, Short.MAX_VALUE)
							                        .add(exitButton))
							                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							                .addContainerGap())
							            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							        );
							        layout.setVerticalGroup(
							            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							            .add(layout.createSequentialGroup()
							                .addContainerGap()
							                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							                .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							                .add(exitButton)
							                .addContainerGap())
							        );

							        jPanel1.getAccessibleContext().setAccessibleName(" ");

							        pack();
								}// </editor-fold>
							
								private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
									this.dispose();
									System.exit(0);
								}
							
								private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) throws IOException, OWLException {
									// TODO add your handling code here:
									FileReader fr = new FileReader("/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/out3.txt");
									ClassObj cl1 = (ClassObj) box2.getSelectedItem();
									IndObj ind1 = (IndObj) box3.getSelectedItem();
									PropertyObj prop = (PropertyObj) box4.getSelectedItem();
									PropertyObj prop2 = new PropertyObj(wine.getFac()
											.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#goesWellWith")));
									
									IndObj ind2 = (IndObj) box5.getSelectedItem();
									OWLObjectPropertyAssertionAxiom assertion = Wine.getFac().getOWLObjectPropertyAssertionAxiom(prop2.getObj(),
											ind1.getIndOf(), ind2.getIndOf());
									System.out.println("ASSERTION: ______________: " + assertion);
									Set<Set<OWLAxiom>> exp2 = wine.getExpGen().getEntailmentExplanations(assertion, 1);
									wine.getRenderer().render(exp2);
									wine.getRenderer().endRendering();
									wine.getOut().flush();
									wine.naturalGeneration();
									wine.removeExtras();
									jTextPane1.read(fr, null);
									jTextPane1.setEditable(false);
									explanationRes.setText(ind1 + " " +  prop2 + " " + ind2);
									fr.close();
									System.out.println("done");
									// naturalGeneration();
									// removeExtras();
							
								}
							
								private void box4ActionPerformed(java.awt.event.ActionEvent evt)
										throws OWLOntologyCreationException, InterruptedException {
									// TODO add your handling code here:
							
									revalidateMatches();
							
								}
							
								public void revalidateMatches() throws OWLOntologyCreationException {
									box5.removeAllItems();
									this.revalidate();
									this.repaint();
							
									PropertyObj prop = (PropertyObj) box4.getSelectedItem();
									IndObj obj = (IndObj) box3.getSelectedItem();
									Set<OWLClass> classes = wine.getReasoner().getObjectPropertyRanges(prop.getObj(), true).getFlattened();
									System.out.println("To string Classes ______: " + classes);
									for (OWLClass cls : classes) {
										Set<OWLNamedIndividual> indi = wine.getReasoner().getInstances(cls, false).getFlattened();
										for (OWLNamedIndividual ind : indi) {
											box5.addItem(new IndObj(ind));
										}
									}
							
								}
							
								private void box3ActionPerformed(java.awt.event.ActionEvent evt) {
									// TODO add your handling code here:
									// box5.removeAllItems();
									// revalidateMatches();
							
								}
							
								private void jButton7ActionPerformed(java.awt.event.ActionEvent evt)
										throws OWLOntologyCreationException, OWLException, IOException {
									JFileChooser fileChooser = new JFileChooser();
									int returnValue = fileChooser.showOpenDialog(null);
									if (returnValue == JFileChooser.APPROVE_OPTION) {
										File filename = fileChooser.getSelectedFile();
										String path = filename.getAbsolutePath();
										System.out.println(path.substring(path.lastIndexOf("."), path.length()));
										if (!(path.substring(path.lastIndexOf("."), path.length()).equals(".owl"))) {
											JOptionPane.showMessageDialog(this, "Please choose a file of type .owl");
										} else {
											fname.setText(path);
											box1.setEnabled(true);
											System.out.println("got the path");
											wine = new Wine(path);
											jButton8.setEnabled(true);
										}
									}
								}
							
								private void box2ActionPerformed(java.awt.event.ActionEvent evt) {
							
									box3.removeAllItems();
									this.revalidate();
									this.repaint();
									ClassObj cl = (ClassObj) box2.getSelectedItem();
							
									System.out.println("_______cl___________: " + cl.toString());
									NodeSet<OWLNamedIndividual> indi = Wine.getReasoner().getInstances(((OWLClass) (cl.getClassOf())), false);
									// System.out.println("tab eh gai wla eldor elgai" + indi);
									for (Node<OWLNamedIndividual> ind : indi) {
							
										Set<OWLNamedIndividual> indis = ind.getEntities();
										// System.out.println("tab eh gai wla eldor elgai" + ind);
										for (OWLNamedIndividual i : indis) {
							
											box3.addItem(new IndObj(i));
										}
							
									}
							
								}
							
								@SuppressWarnings("deprecation")
								private void box1ActionPerformed(java.awt.event.ActionEvent evt) {
									// TODO add your handling code here:
									if (box1.getSelectedIndex() == 0) {
										JOptionPane.showMessageDialog(this, "Please choose an explanation type");
									} else {
										switch (box1.getSelectedIndex()) {
							
										case 1: {
											label2.setText("Choose Class of 1st individual:");
											// box2.setSelectedItem("Classes");
							
											label3.setText("Choose Instance:");
											box3.setSelectedItem("Instances");
							
											label4.setText("Choose Object Property:");
											box4.setSelectedItem("Object Properties");
							
											label5.setText("Choose corresponding instance:");
											box2.setSelectedItem("Instances");
							
											box2.setEnabled(true);
											box3.setEnabled(true);
											box4.setEnabled(true);
											box5.setEnabled(true);
							
											for (OWLObjectProperty obj : wine.getObjectProps()) {
												box4.addItem(new PropertyObj(obj));
											}
										}
											break;
							
										case 2: {
											box2.setEnabled(false);
											box3.setEnabled(false);
											box4.setEnabled(false);
											box5.setEnabled(false);
							
										}
											break;
							
										case 3: {
											label2.setText("Choose Class of instance:");
											box2.setSelectedItem("Classes");
											label3.setText("Choose Instance:");
											box3.setSelectedItem("Instances");
											box2.setEnabled(true);
											box3.setEnabled(true);
											box4.setEnabled(false);
											box5.setEnabled(false);
							
										}
											break;
							
										case 4: {
											label2.setText("Choose Subclass:");
											box2.setSelectedItem("Subclasses");
							
											label3.setText("Choose Superclass:");
											box2.setSelectedItem("Superclasses");
											box2.setEnabled(true);
											box3.setEnabled(true);
											box4.setEnabled(false);
											box5.setEnabled(false);
							
										}
											break;
							
										case 5: {
											label2.setText("Choose Class:");
											box2.setSelectedItem("Classes");
											box2.setEnabled(true);
											box3.setEnabled(false);
											box4.setEnabled(false);
											box5.setEnabled(false);
							
										}
											break;
							
										default: {
							
										}
											break;
										}
							
									}
							
									Set<OWLClass> classes = wine.getClasses();
									System.out.println(classes.toString());
									for (OWLClass cls : classes) {
										IRI ir = cls.getIRI();
										String iri = ir.toString();
										// try{
										// box2.addItem(iri.substring(60, 63) + ":" + ir.getShortForm());
										// } catch(StringIndexOutOfBoundsException e){
										// box2.addItem("Owl:Thing");
										// }
										box2.addItem(new ClassObj(cls));
									}
							
								}
							
								private void box5ActionPerformed(java.awt.event.ActionEvent evt) {
									// TODO add your handling code here:
							
									
							
								}
							
								/**
								 * @param args
								 *            the command line arguments
								 */
								public static void main(String args[]) {
									/* Set the Nimbus look and feel */
									// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
									// code (optional) ">
									/*
									 * If Nimbus (introduced in Java SE 6) is not available, stay with the
									 * default look and feel. For details see
									 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
									 * html
									 */
									try {
										javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels = javax.swing.UIManager
												.getInstalledLookAndFeels();
										for (int idx = 0; idx < installedLookAndFeels.length; idx++)
											if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
												javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
												break;
											}
									} catch (ClassNotFoundException ex) {
										java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
									} catch (InstantiationException ex) {
										java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
									} catch (IllegalAccessException ex) {
										java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
									} catch (javax.swing.UnsupportedLookAndFeelException ex) {
										System.out.println("is it here??????????????????????????");
										java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
									}
									// </editor-fold>
							
									/* Create and display the form */
									java.awt.EventQueue.invokeLater(new Runnable() {
										public void run() {
											new Gui().setVisible(true);
										}
									});
								}
							
								// Variables declaration - do not modify
								private javax.swing.JComboBox box1;
							
								private javax.swing.JComboBox<ClassObj> box2;
								private javax.swing.JComboBox box3;
								private javax.swing.JComboBox<PropertyObj> box4;
								private javax.swing.JComboBox<IndObj> box5;
								private javax.swing.ButtonGroup buttonGroup1;
								private javax.swing.JButton exitButton;
								private javax.swing.JLabel explanationRes;
								private javax.swing.JLabel fname;
								private javax.swing.JButton jButton7;
								private javax.swing.JButton jButton8;
								private javax.swing.JLabel jLabel1;
								private javax.swing.JLabel jLabel2;
								private javax.swing.JLabel jLabel3;
								private javax.swing.JLabel jLabel6;
								private javax.swing.JPanel jPanel1;
								private javax.swing.JPanel jPanel2;
								private javax.swing.JScrollPane jScrollPane1;
								private javax.swing.JTextPane jTextPane1;
								private javax.swing.JLabel label1;
								private javax.swing.JLabel label2;
								private javax.swing.JLabel label3;
								private javax.swing.JLabel label4;
								private javax.swing.JLabel label5;
								// End of variables declaration
							
							}
