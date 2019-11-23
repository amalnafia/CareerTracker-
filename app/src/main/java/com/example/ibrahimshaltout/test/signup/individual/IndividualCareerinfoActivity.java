package com.example.ibrahimshaltout.test.signup.individual;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.CollegeDataClass;
import com.example.ibrahimshaltout.test.dataclass.CorporateDataClass;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.example.ibrahimshaltout.test.dataclass.UniversityDataClass;
import com.example.ibrahimshaltout.test.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IndividualCareerinfoActivity extends AppCompatActivity {

    private static final int PICKFILE_RESULT_CODE = 1;
    MultiAutoCompleteTextView inputskills, inputinterest, inputExperience;
    AutoCompleteTextView school_type_name, university_name, college_name, specialization_name, grade_name, company_name, job_title, department;
    private EditText inputSchool, inputfieldof_diploma, inputfieldof_masters, inputfieldof_doctorate,
            inputstartYear, inputendYear, inputCV;

    TextInputLayout inputSchoolLayout, inputSchoolTypeLayout, inputUniversityLayout, inputSpecializationLayout,
            inputGradeLayout, inputfieldof_diplomaLayout, inputfieldof_mastersLayout, inputfieldof_doctorateLayout,
            inputcollegeLayout, inputstartYearLayout, inputendYearLayout, inputCompanyLayout, inputPositionLayout,
            inputDepLayout, inputskillsLayout, inputinterestLayout, inputExperienceLayout;

    private Button btnNext, btnCv;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    DatabaseReference db;
    DatabaseReference databaseReference;

    private int spinnerItemSelcected2;
    MaterialBetterSpinner materialDesignSpinner2;
    ArrayAdapter<String> arrayAdapter2;
    private StorageReference documentAttach;

    ArrayList clgNameDataSnapShot = new ArrayList<>();
    ArrayList uniNameDataSnapShot = new ArrayList<>();
    ArrayList<String> uniDepDataSnapShot = new ArrayList<>();
    ArrayList corporateNameDataSnapShot = new ArrayList<>();
    ArrayList uniNameID = new ArrayList<>();


    String[] gradeArray = {"Grade 1", "Grade 2", "Grade 3", "Grade 4", "Grade 5"};
    private String[] qualificationList = {"Preparatory School", "High School", "Undergraduate", "Fresh graduate", "Diploma ", "Masters", "Doctorate ", "Employee "};
    String[] skillsArray = {"Dwight D. Eisenhower", "John F. Kennedy", "Lyndon B. Johnson", "Richard Nixon", "Gerald Ford", "Jimmy Carter",
            "Ronald Reagan", "George H. W. Bush", "Bill Clinton", "George W. Bush", "Barack Obama"};
    String[] interestsArray = {"Dwight D. Eisenhower", "John F. Kennedy", "Lyndon B. Johnson", "Richard Nixon", "Gerald Ford", "Jimmy Carter",
            "Ronald Reagan", "George H. W. Bush", "Bill Clinton", "George W. Bush", "Barack Obama"};
    String[] experienceArray = {"Dwight D. Eisenhower", "John F. Kennedy", "Lyndon B. Johnson", "Richard Nixon", "Gerald Ford", "Jimmy Carter",
            "Ronald Reagan", "George H. W. Bush", "Bill Clinton", "George W. Bush", "Barack Obama"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_careerinfo);
        auth = FirebaseAuth.getInstance();
        final String currentUser = auth.getCurrentUser().getUid();

        auth = FirebaseAuth.getInstance();
        btnNext = (Button) findViewById(R.id.individual_next1);
        inputSchool = (EditText) findViewById(R.id.School_Name);
        inputfieldof_diploma = (EditText) findViewById(R.id.field_diploma);
        inputfieldof_masters = (EditText) findViewById(R.id.field_masters);
        inputfieldof_doctorate = (EditText) findViewById(R.id.field_doctorate);
        inputstartYear = (EditText) findViewById(R.id.start_year);
        inputendYear = (EditText) findViewById(R.id.end_year);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        inputSchoolLayout = (TextInputLayout) findViewById(R.id.layout_school_name);
        inputSchoolTypeLayout = (TextInputLayout) findViewById(R.id.layoutSchool_type_name);
        inputUniversityLayout = (TextInputLayout) findViewById(R.id.layout_university_name);
        inputSpecializationLayout = (TextInputLayout) findViewById(R.id.layoutSpecialization_name);
        inputGradeLayout = (TextInputLayout) findViewById(R.id.layoutGrade_name);
        inputfieldof_diplomaLayout = (TextInputLayout) findViewById(R.id.layoutField_diploma);
        inputfieldof_mastersLayout = (TextInputLayout) findViewById(R.id.layoutField_masters);
        inputfieldof_doctorateLayout = (TextInputLayout) findViewById(R.id.layoutField_doctorate);
        inputcollegeLayout = (TextInputLayout) findViewById(R.id.layout_college_name);
        inputstartYearLayout = (TextInputLayout) findViewById(R.id.layoutStart_year);
        inputendYearLayout = (TextInputLayout) findViewById(R.id.layoutEnd_year);
        inputCompanyLayout = (TextInputLayout) findViewById(R.id.LayoutCompany_name);
        inputPositionLayout = (TextInputLayout) findViewById(R.id.layoutJob_title);
        inputDepLayout = (TextInputLayout) findViewById(R.id.layout_department);
        inputskillsLayout = (TextInputLayout) findViewById(R.id.LayoutSkills_talents);
        inputinterestLayout = (TextInputLayout) findViewById(R.id.layout_interests);
        inputExperienceLayout = (TextInputLayout) findViewById(R.id.Layout_experience);


        ArrayAdapter<String> skillsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, skillsArray);
        inputskills = (MultiAutoCompleteTextView) findViewById(R.id.skills_talents);
        inputskills.setThreshold(1);
        inputskills.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        inputskills.setAdapter(skillsadapter);
        ArrayAdapter<String> experienceadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, experienceArray);
        inputExperience = (MultiAutoCompleteTextView) findViewById(R.id.experience);
        inputExperience.setThreshold(1);
        inputExperience.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        inputExperience.setAdapter(experienceadapter);

        ArrayAdapter<String> Interestsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, interestsArray);
        inputinterest = (MultiAutoCompleteTextView) findViewById(R.id.interests);
        inputinterest.setThreshold(1);
        inputinterest.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        inputinterest.setAdapter(Interestsadapter);

        ArrayAdapter<String> schoolNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, interestsArray);
        school_type_name = (AutoCompleteTextView) findViewById(R.id.school_type_name);
        school_type_name.setThreshold(1);
        school_type_name.setAdapter(schoolNameAdapter);

        ArrayAdapter<String> uniNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, uniNameDataSnapShot);
        university_name = (AutoCompleteTextView) findViewById(R.id.university_name);
        university_name.setThreshold(1);
        university_name.setAdapter(uniNameAdapter);
//        university_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String currentUni= String.valueOf(parent.getId());
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        ArrayAdapter<String> collegeNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, clgNameDataSnapShot);
        college_name = (AutoCompleteTextView) findViewById(R.id.college_name);
        college_name.setThreshold(1);
        college_name.setAdapter(collegeNameAdapter);

        ArrayAdapter<String> specAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, uniDepDataSnapShot);
        specialization_name = (AutoCompleteTextView) findViewById(R.id.specialization_name);
        specialization_name.setThreshold(1);
        specialization_name.setAdapter(specAdapter);

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, gradeArray);
        grade_name = (AutoCompleteTextView) findViewById(R.id.grade_name);
        grade_name.setThreshold(1);
        grade_name.setAdapter(gradeAdapter);

        ArrayAdapter<String> companyNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, corporateNameDataSnapShot);
        company_name = (AutoCompleteTextView) findViewById(R.id.company_name);
        company_name.setThreshold(1);
        company_name.setAdapter(companyNameAdapter);

        ArrayAdapter<String> jobTitleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, interestsArray);
        job_title = (AutoCompleteTextView) findViewById(R.id.job_title);
        job_title.setThreshold(1);
        job_title.setAdapter(jobTitleAdapter);

        ArrayAdapter<String> ClgFldAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, interestsArray);
        department = (AutoCompleteTextView) findViewById(R.id.department);
        department.setThreshold(1);
        department.setAdapter(ClgFldAdapter);

        db = FirebaseDatabase.getInstance().getReference();
        db.child("Universities").child("Colleges").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchCollageData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        db.child("Universities").child("List_Of_Universities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                fetchUniversityData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        db.child("Universities").child("List_Of_Universities").child("-LcM-o413NPEtHnxoexo").child("uniDepField").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                fetchDepData(dataSnapshot);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    db.child("Corporates").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            fetchCorporateNameData(dataSnapshot);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


        inputSchoolLayout.setVisibility(View.GONE);
        inputSchoolTypeLayout.setVisibility(View.GONE);
        inputstartYearLayout.setVisibility(View.GONE);
        inputendYearLayout.setVisibility(View.GONE);
        inputinterestLayout.setVisibility(View.GONE);
        inputskillsLayout.setVisibility(View.GONE);
        inputExperienceLayout.setVisibility(View.GONE);
        inputinterestLayout.setVisibility(View.GONE);
        inputUniversityLayout.setVisibility(View.GONE);
        inputSpecializationLayout.setVisibility(View.GONE);
        inputGradeLayout.setVisibility(View.GONE);
        inputfieldof_diplomaLayout.setVisibility(View.GONE);
        inputfieldof_mastersLayout.setVisibility(View.GONE);
        inputfieldof_doctorateLayout.setVisibility(View.GONE);
        inputcollegeLayout.setVisibility(View.GONE);
        inputCompanyLayout.setVisibility(View.GONE);
        inputPositionLayout.setVisibility(View.GONE);
        inputDepLayout.setVisibility(View.GONE);

        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, qualificationList);
        materialDesignSpinner2 = (MaterialBetterSpinner) findViewById(R.id.qualification_level);
        materialDesignSpinner2.setAdapter(arrayAdapter2);
        materialDesignSpinner2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinnerItemSelcected2 = position;
                if (spinnerItemSelcected2 == 0) {
                    inputSchoolLayout.setVisibility(View.VISIBLE);
                    inputSchoolTypeLayout.setVisibility(View.VISIBLE);
                    inputstartYearLayout.setVisibility(View.GONE);
                    inputendYearLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputskillsLayout.setVisibility(View.VISIBLE);
                    inputExperienceLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputUniversityLayout.setVisibility(View.GONE);
                    inputSpecializationLayout.setVisibility(View.GONE);
                    inputGradeLayout.setVisibility(View.GONE);
                    inputfieldof_diplomaLayout.setVisibility(View.GONE);
                    inputfieldof_mastersLayout.setVisibility(View.GONE);
                    inputfieldof_doctorateLayout.setVisibility(View.GONE);
                    inputcollegeLayout.setVisibility(View.GONE);
                    inputCompanyLayout.setVisibility(View.GONE);
                    inputPositionLayout.setVisibility(View.GONE);
                    inputDepLayout.setVisibility(View.GONE);
                } else if (spinnerItemSelcected2 == 1) {
                    inputSchoolLayout.setVisibility(View.VISIBLE);
                    inputSchoolTypeLayout.setVisibility(View.VISIBLE);
                    inputstartYearLayout.setVisibility(View.GONE);
                    inputendYearLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputskillsLayout.setVisibility(View.VISIBLE);
                    inputExperienceLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputUniversityLayout.setVisibility(View.GONE);
                    inputSpecializationLayout.setVisibility(View.GONE);
                    inputGradeLayout.setVisibility(View.GONE);
                    inputfieldof_diplomaLayout.setVisibility(View.GONE);
                    inputfieldof_mastersLayout.setVisibility(View.GONE);
                    inputfieldof_doctorateLayout.setVisibility(View.GONE);
                    inputcollegeLayout.setVisibility(View.GONE);
                    inputCompanyLayout.setVisibility(View.GONE);
                    inputPositionLayout.setVisibility(View.GONE);
                    inputDepLayout.setVisibility(View.GONE);
                } else if (spinnerItemSelcected2 == 2) {
                    inputUniversityLayout.setVisibility(View.VISIBLE);
                    inputSpecializationLayout.setVisibility(View.VISIBLE);
                    inputcollegeLayout.setVisibility(View.VISIBLE);
                    inputstartYearLayout.setVisibility(View.GONE);
                    inputendYearLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputskillsLayout.setVisibility(View.VISIBLE);
                    inputExperienceLayout.setVisibility(View.GONE);
                    inputSchoolLayout.setVisibility(View.GONE);
                    inputSchoolTypeLayout.setVisibility(View.GONE);
                    inputGradeLayout.setVisibility(View.GONE);
                    inputfieldof_diplomaLayout.setVisibility(View.GONE);
                    inputfieldof_mastersLayout.setVisibility(View.GONE);
                    inputfieldof_doctorateLayout.setVisibility(View.GONE);
                    inputCompanyLayout.setVisibility(View.GONE);
                    inputPositionLayout.setVisibility(View.GONE);
                    inputDepLayout.setVisibility(View.GONE);
                } else if (spinnerItemSelcected2 == 3) {
                    inputUniversityLayout.setVisibility(View.VISIBLE);
                    inputSpecializationLayout.setVisibility(View.VISIBLE);
                    inputGradeLayout.setVisibility(View.VISIBLE);
                    inputcollegeLayout.setVisibility(View.VISIBLE);
                    inputstartYearLayout.setVisibility(View.GONE);
                    inputendYearLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputskillsLayout.setVisibility(View.VISIBLE);
                    inputExperienceLayout.setVisibility(View.GONE);
                    inputSchoolLayout.setVisibility(View.GONE);
                    inputSchoolTypeLayout.setVisibility(View.GONE);
                    inputfieldof_diplomaLayout.setVisibility(View.GONE);
                    inputfieldof_mastersLayout.setVisibility(View.GONE);
                    inputfieldof_doctorateLayout.setVisibility(View.GONE);
                    inputCompanyLayout.setVisibility(View.GONE);
                    inputPositionLayout.setVisibility(View.GONE);
                    inputDepLayout.setVisibility(View.GONE);
                } else if (spinnerItemSelcected2 == 4) {
                    inputSchoolLayout.setVisibility(View.GONE);
                    inputSchoolTypeLayout.setVisibility(View.GONE);
                    inputUniversityLayout.setVisibility(View.VISIBLE);
                    inputSpecializationLayout.setVisibility(View.VISIBLE);
                    inputGradeLayout.setVisibility(View.GONE);
                    inputcollegeLayout.setVisibility(View.VISIBLE);
                    inputfieldof_diplomaLayout.setVisibility(View.VISIBLE);
                    inputfieldof_mastersLayout.setVisibility(View.GONE);
                    inputfieldof_doctorateLayout.setVisibility(View.GONE);
                    inputstartYearLayout.setVisibility(View.GONE);
                    inputendYearLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputExperienceLayout.setVisibility(View.VISIBLE);
                    inputskillsLayout.setVisibility(View.VISIBLE);
                    inputCompanyLayout.setVisibility(View.VISIBLE);
                    inputPositionLayout.setVisibility(View.VISIBLE);
                    inputDepLayout.setVisibility(View.VISIBLE);
                } else if (spinnerItemSelcected2 == 5) {
                    inputSchoolLayout.setVisibility(View.GONE);
                    inputSchoolTypeLayout.setVisibility(View.GONE);
                    inputUniversityLayout.setVisibility(View.VISIBLE);
                    inputSpecializationLayout.setVisibility(View.VISIBLE);
                    inputGradeLayout.setVisibility(View.GONE);
                    inputcollegeLayout.setVisibility(View.VISIBLE);
                    inputfieldof_diplomaLayout.setVisibility(View.GONE);
                    inputfieldof_mastersLayout.setVisibility(View.VISIBLE);
                    inputfieldof_doctorateLayout.setVisibility(View.GONE);
                    inputstartYearLayout.setVisibility(View.GONE);
                    inputendYearLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputExperienceLayout.setVisibility(View.VISIBLE);
                    inputskillsLayout.setVisibility(View.VISIBLE);
                    inputCompanyLayout.setVisibility(View.VISIBLE);
                    inputPositionLayout.setVisibility(View.VISIBLE);
                    inputDepLayout.setVisibility(View.VISIBLE);
                } else if (spinnerItemSelcected2 == 6) {
                    inputSchoolLayout.setVisibility(View.GONE);
                    inputSchoolTypeLayout.setVisibility(View.GONE);
                    inputUniversityLayout.setVisibility(View.VISIBLE);
                    inputSpecializationLayout.setVisibility(View.VISIBLE);
                    inputGradeLayout.setVisibility(View.GONE);
                    inputcollegeLayout.setVisibility(View.VISIBLE);
                    inputfieldof_diplomaLayout.setVisibility(View.GONE);
                    inputfieldof_mastersLayout.setVisibility(View.GONE);
                    inputfieldof_doctorateLayout.setVisibility(View.VISIBLE);
                    inputstartYearLayout.setVisibility(View.GONE);
                    inputendYearLayout.setVisibility(View.GONE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputExperienceLayout.setVisibility(View.VISIBLE);
                    inputskillsLayout.setVisibility(View.VISIBLE);
                    inputCompanyLayout.setVisibility(View.VISIBLE);
                    inputPositionLayout.setVisibility(View.VISIBLE);
                    inputDepLayout.setVisibility(View.VISIBLE);
                } else if (spinnerItemSelcected2 == 7) {
                    inputSchoolLayout.setVisibility(View.GONE);
                    inputSchoolTypeLayout.setVisibility(View.GONE);
                    inputUniversityLayout.setVisibility(View.VISIBLE);
                    inputSpecializationLayout.setVisibility(View.VISIBLE);
                    inputGradeLayout.setVisibility(View.GONE);
                    inputfieldof_diplomaLayout.setVisibility(View.GONE);
                    inputfieldof_mastersLayout.setVisibility(View.GONE);
                    inputfieldof_doctorateLayout.setVisibility(View.GONE);
                    inputcollegeLayout.setVisibility(View.VISIBLE);
                    inputstartYearLayout.setVisibility(View.GONE);
                    inputendYearLayout.setVisibility(View.GONE);
                    inputskillsLayout.setVisibility(View.VISIBLE);
                    inputExperienceLayout.setVisibility(View.VISIBLE);
                    inputinterestLayout.setVisibility(View.VISIBLE);
                    inputCompanyLayout.setVisibility(View.VISIBLE);
                    inputPositionLayout.setVisibility(View.VISIBLE);
                    inputDepLayout.setVisibility(View.VISIBLE);

                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);

                String listSkillString = inputskills.getText().toString();
                final String SkillstrArray[] = listSkillString.split(",");
                List listSkill = new ArrayList<String>(Arrays.asList(SkillstrArray));

                String listInterestsString = inputinterest.getText().toString();
                final String InterestsstrArray[] = listInterestsString.split(",");
                List listInterests = new ArrayList<String>(Arrays.asList(InterestsstrArray));

                String listExperienceString = inputExperience.getText().toString();
                final String experiencestrArray[] = listExperienceString.split(",");
                List listExperience = new ArrayList<String>(Arrays.asList(experiencestrArray));

                final String schoolName = inputSchool.getText().toString().trim();
                final String universityName = university_name.getText().toString().trim();
                final String specialization = specialization_name.getText().toString().trim();
                final String grade = grade_name.getText().toString().trim();
                final String fieldOfDiploma = inputfieldof_diploma.getText().toString().trim();
                final String fieldOfMasters = inputfieldof_masters.getText().toString().trim();
                final String fieldOfDoctorate = inputfieldof_doctorate.getText().toString().trim();
                final String collegeName = college_name.getText().toString().trim();
                final String startYear = inputstartYear.getText().toString().trim();
                final String endYear = inputendYear.getText().toString().trim();
                final String companyName = company_name.getText().toString().trim();
                final String jobTitle = job_title.getText().toString().trim();
                final String department = IndividualCareerinfoActivity.this.department.getText().toString().trim();

                final String SchoolType = school_type_name.getText().toString().trim();

                String currentUser = auth.getCurrentUser().getUid();

                Toast.makeText(getApplicationContext(), currentUser, Toast.LENGTH_SHORT).show();
//                if (TextUtils.isEmpty(schoolname)) {
//                    Toast.makeText(getApplicationContext(), "Enter School Name!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(collegename)) {
//                    Toast.makeText(getApplicationContext(), "Enter College Name!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(level)) {
//                    Toast.makeText(getApplicationContext(), "Enter your level!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(skills)) {
//                    Toast.makeText(getApplicationContext(), "Enter skills!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(intresets)) {
//                    Toast.makeText(getApplicationContext(), "Enter intresets!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(company)) {
//                    Toast.makeText(getApplicationContext(), "Enter Company Name!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(jobTitle)) {
//                    Toast.makeText(getApplicationContext(), "Enter jobTitle!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(department)) {
//                    Toast.makeText(getApplicationContext(), "Enter Department!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(CV)) {
//                    Toast.makeText(getApplicationContext(), "attach your CV!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                progressBar.setVisibility(View.VISIBLE);
                IndividualDataClass individualDataClass = new IndividualDataClass();
                String TheLevelOfQualification = signUpAs();
                individualDataClass.setQualification_Level(TheLevelOfQualification);
                individualDataClass.setSchool_Name(schoolName);
                individualDataClass.setSchool_Type(SchoolType);
                individualDataClass.setUniversity_Name(universityName);
                individualDataClass.setDep_Specialization(specialization);
                individualDataClass.setGrade(grade);
                individualDataClass.setDiploma_Field(fieldOfDiploma);
                individualDataClass.setMaster_Field(fieldOfMasters);
                individualDataClass.setDoctorate_Field(fieldOfDoctorate);
                individualDataClass.setCollegeName(collegeName);
                individualDataClass.setStart_Year_Date(startYear);
                individualDataClass.setEnd_Year_Date(endYear);
                individualDataClass.setCompany_Name(companyName);
                individualDataClass.setJobTitle(jobTitle);
                individualDataClass.setDepartment(department);
                individualDataClass.setInterestsList(listInterests);
                individualDataClass.setSkills_List(listSkill);
                individualDataClass.setExperience(listExperience);

                databaseReference.child("qualificationLevel").setValue(individualDataClass.qualification_Level);
                databaseReference.child("schoolName").setValue(individualDataClass.school_Name);
                databaseReference.child("schoolType").setValue(individualDataClass.school_Type);
                databaseReference.child("universityName").setValue(individualDataClass.university_Name);
                databaseReference.child("collegeName").setValue(individualDataClass.collegeName);
                databaseReference.child("depSpecialization").setValue(individualDataClass.dep_Specialization);
                databaseReference.child("grade").setValue(individualDataClass.grade);
                databaseReference.child("diplomaField").setValue(individualDataClass.diploma_Field);
                databaseReference.child("masterField").setValue(individualDataClass.master_Field);
                databaseReference.child("doctorateField").setValue(individualDataClass.doctorate_Field);
                databaseReference.child("startYearDate").setValue(individualDataClass.start_Year_Date);
                databaseReference.child("endYearDate").setValue(individualDataClass.end_Year_Date);
                databaseReference.child("skillsList").setValue(individualDataClass.skills_List);
                databaseReference.child("interestsList").setValue(individualDataClass.interestsList);
                databaseReference.child("experience").setValue(individualDataClass.experience);
                databaseReference.child("companyName").setValue(individualDataClass.company_Name);
                databaseReference.child("jobTitle").setValue(individualDataClass.jobTitle);
                databaseReference.child("department").setValue(individualDataClass.department);


                registerSuccessPopUp();

                // LAUNCH activity after certain time period
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        IndividualCareerinfoActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                auth.signOut();
                                Intent intent = new Intent(IndividualCareerinfoActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
//                                SweetToast.info(IndividualCareerinfoActivity.this, " verify Your Account and enter your Email & Password");

                            }
                        });
                    }
                }, 8000);

            }
        });
    }

    private void registerSuccessPopUp() {
        // Custom Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(IndividualCareerinfoActivity.this);
        View view = LayoutInflater.from(IndividualCareerinfoActivity.this).inflate(R.layout.register_data_success_popup, null);

        //ImageButton imageButton = view.findViewById(R.id.successIcon);
        //imageButton.setImageResource(R.drawable.logout);
        builder.setCancelable(false);
        builder.setView(view);
        builder.show();
    }

    private void fetchUniversityData(DataSnapshot dataSnapshot) {
        UniversityDataClass universityDataClass = null;
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            universityDataClass = x.getValue(UniversityDataClass.class);
            uniNameDataSnapShot.add(universityDataClass.getUniversityName());
        }
    }

    private void fetchDepData(DataSnapshot dataSnapshot) {
        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
        };
        ArrayList<String> yourStringArray = dataSnapshot.getValue(t);
        uniDepDataSnapShot.addAll(yourStringArray);
    }

    private void fetchCorporateNameData(DataSnapshot dataSnapshot) {
        CorporateDataClass corporateDataClass = null;
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            corporateDataClass = x.getValue(CorporateDataClass.class);
            corporateNameDataSnapShot.add(corporateDataClass.getCorporateName());
        }
    }

    private String signUpAs() {
        String theLevel = "null";
        for (int i = 0; i < qualificationList.length; i++) {
            if (spinnerItemSelcected2 == i) {
                theLevel = qualificationList[i];
            }
        }
        return (theLevel);
    }

    private void fetchCollageData(DataSnapshot dataSnapshot) {
        CollegeDataClass collegeDataClass = null;
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            collegeDataClass = x.getValue(CollegeDataClass.class);
            clgNameDataSnapShot.add(collegeDataClass.getCollegeFieldName());
        }

    }
}


