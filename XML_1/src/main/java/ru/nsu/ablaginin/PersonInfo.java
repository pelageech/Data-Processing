package ru.nsu.ablaginin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class PersonInfo {
    private final StringBuilder stringBuilder = new StringBuilder().append("{ ");

    /*
     * id
     * gender - male or female
     * first
     * family
     * children
     * spouse
     * parent
     * siblings
     * */
    private String id;
    private String gender;
    private Integer siblingsNumber;
    private Integer childrenNumber;

    private String firstName;

    private String familyName;


    private String spouseId;
    private String spouseName;

    private String wifeId;
    private String wifeName;

    private String husbandId;
    private String husbandName;


    private String motherId;
    private String motherName;

    private String fatherId;
    private String fatherName;


    private Set<String> childrenIds = new HashSet<>();
    private Set<String> childrenNames = new HashSet<>();

    private Set<String> sonsIds = new HashSet<>();
    private Set<String> sonsNames = new HashSet<>();

    private Set<String> daughtersIds = new HashSet<>();
    private Set<String> daughtersNames = new HashSet<>();


    private Set<String> brothersIds = new HashSet<>();
    private Set<String> brothersNames = new HashSet<>();
    private Set<String> sisterIds = new HashSet<>();
    private Set<String> sisterNames = new HashSet<>();
    private Set<String> siblingsIds = new HashSet<>();
    private Set<String> siblingsNames = new HashSet<>();


    private Set<String> parentsIds = new HashSet<>();
    private Set<String> parentsNames = new HashSet<>();

    public void merge(PersonInfo p) {
        if (p == null)
            return;

        validateMergeElement(id, p.id, this::setId);
        validateMergeElement(firstName, p.firstName, this::setFirstName);
        validateMergeElement(familyName, p.familyName, this::setFamilyName);
        validateMergeElement(gender, p.gender, this::setGender);
        validateMergeElement(motherId, p.motherId, this::setMotherId);
        validateMergeElement(fatherId, p.fatherId, this::setFatherId);
        validateMergeElement(motherName, p.motherName, this::setMotherName);
        validateMergeElement(fatherName, p.fatherName, this::setFatherName);
        validateMergeElement(spouseId, p.spouseId, this::setSpouseId);
        validateMergeElement(husbandId, p.husbandId, this::setHusbandId);
        validateMergeElement(wifeId, p.wifeId, this::setWifeId);
        validateMergeElement(spouseName, p.spouseName, this::setSpouseName);
        validateMergeElement(husbandName, p.husbandName, this::setHusbandName);
        validateMergeElement(wifeName, p.wifeName, this::setWifeName);
        validateMergeElement(childrenNumber, p.childrenNumber, this::setChildrenNumber);
        validateMergeElement(siblingsNumber, p.siblingsNumber, this::setSiblingsNumber);

        parentsIds.addAll(p.parentsIds);

        childrenIds.addAll(p.getChildrenIds());
        sonsIds.addAll(p.getSonsIds());
        daughtersIds.addAll(p.getDaughtersIds());
        childrenNames.addAll(p.getChildrenNames());
        sonsNames.addAll(p.getSonsNames());
        daughtersNames.addAll(p.getDaughtersNames());

        siblingsIds.addAll(p.getSiblingsIds());
        brothersIds.addAll(p.getBrothersIds());
        sisterIds.addAll(p.getSisterIds());
        siblingsNames.addAll(p.getSiblingsNames());
        brothersNames.addAll(p.getBrothersNames());
        sisterNames.addAll(p.getSisterNames());
    }

    private void validateMergeElement(String obj, String newValue, Consumer<String> consumer) {
        if (obj == null) {
            consumer.accept(newValue);
        }
    }

    private void validateMergeElement(Integer obj, Integer newValue, Consumer<Integer> consumer) {
        if (obj == null) {
            consumer.accept(newValue);
        }
    }

    //setters
    public void setId(String id) {
        this.id = id;
    }

    public void setGender(String gender) {
        if (gender == null) return;
        switch (gender.toLowerCase()) {
            case "female", "male" -> this.gender = gender.toLowerCase();
            case "m" -> this.gender = "male";
            case "f" -> this.gender = "female";
        }
    }

    public void setFullName(String fullName) {
        String[] names = fullName.split("\\s+");
        this.firstName = names[0];
        this.familyName = names[1];
    }

    public void setSiblingsNumber(Integer siblingsNumber) {
        this.siblingsNumber = siblingsNumber;
    }

    public void setSiblingsNumber(String siblingsNumber) {
        this.siblingsNumber = Integer.parseInt(siblingsNumber);
    }

    public void setChildrenNumber(Integer childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public void setChildrenNumber(String childrenNumber) {
        this.childrenNumber = Integer.parseInt(childrenNumber);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setSpouse(String spouse) {
        this.spouseName = spouse;
    }

    public void setWife(String wife) {
        this.wifeId = wife;
    }

    public void setHusband(String husband) {
        this.husbandId = husband;
    }

    public void setMother(String mother) {
        this.motherName = trimToNormal(mother);
    }

    public void setFather(String father) {
        this.fatherName = trimToNormal(father);
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public void setWifeId(String wifeId) {
        this.wifeId = wifeId;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public void setHusbandId(String husbandId) {
        this.husbandId = husbandId;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setChildrenIds(Set<String> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public void setChildrenNames(Set<String> childrenNames) {
        this.childrenNames = childrenNames;
    }

    public void setSonsIds(Set<String> sonsIds) {
        this.sonsIds = sonsIds;
    }

    public void setSonsNames(Set<String> sonsNames) {
        this.sonsNames = sonsNames;
    }

    public void setDaughtersIds(Set<String> daughtersIds) {
        this.daughtersIds = daughtersIds;
    }

    public void setDaughtersNames(Set<String> daughtersNames) {
        this.daughtersNames = daughtersNames;
    }

    public void setBrothersIds(Set<String> brothersIds) {
        this.brothersIds = brothersIds;
    }

    public void setBrothersNames(Set<String> brothersNames) {
        this.brothersNames = brothersNames;
    }

    public void setSisterIds(Set<String> sisterIds) {
        this.sisterIds = sisterIds;
    }

    public void setSisterNames(Set<String> sisterNames) {
        this.sisterNames = sisterNames;
    }

    public void setSiblingsIds(Set<String> siblingsIds) {
        this.siblingsIds = siblingsIds;
    }

    public void setSiblingsNames(Set<String> siblingsNames) {
        this.siblingsNames = siblingsNames;
    }

    public void setParentsIds(Set<String> parentsIds) {
        this.parentsIds = parentsIds;
    }

    public void setParentsNames(Set<String> parentsNames) {
        this.parentsNames = parentsNames;
    }

    public void addChild(String child) {
        this.childrenNames.add(trimToNormal(child));
    }

    public void addChildId(String child) {
        this.childrenIds.add(child);
    }


    public void addSonId(String child) {
        this.sonsIds.add(child);
    }


    public void addDaughterId(String child) {
        this.daughtersIds.add(child);
    }

    public void addSiblingName(String val) {
        siblingsNames.add(trimToNormal(val));
    }

    public void addBrotherId(String val) {
        brothersIds.addAll(List.of(val.trim().split("\\s+")));
    }

    public void addBrotherName(String val) {
        brothersNames.add(trimToNormal(val));
    }

    public void addSisterId(String val) {
        sisterIds.addAll(List.of(val.trim().split("\\s+")));
    }

    public void addSisterName(String val) {
        sisterNames.add(trimToNormal(val));
    }

    public void addSiblingIds(String sibling) {
        siblingsIds.addAll(List.of(sibling.trim().split("\\s+")));
    }

    public void addSiblingId(String sibling) {
        siblingsIds.add(sibling);
    }

    public void addParent(String parent) {
        parentsIds.add(parent);
    }

    //toString
    @Override
    public String toString() {

        return this
                .addString("id", id)
                .addString("first", firstName)
                .addString("familyName", familyName)
                .addString("gender", gender)
                .addString("siblingsNumber", siblingsNumber)
                .addString("childrenNumber", childrenNumber)
                .addString("spouseName", spouseName)
                .addString("spouseId", spouseId)
                .addString("wifeId", wifeId)
                .addString("wifeName", wifeName)
                .addString("husbandId", husbandId)
                .addString("husbandName", husbandName)
                .addString("motherId", motherId)
                .addString("motherName", motherName)
                .addString("fatherId", fatherId)
                .addString("fatherName", fatherName)
                .addSet("childrenIds", childrenIds)
                .addSet("childrenNames", childrenNames)
                .addSet("sonsIds", sonsIds)
                .addSet("sonsNames", sonsNames)
                .addSet("daughtersIds", daughtersIds)
                .addSet("daughtersNames", daughtersNames)
                .addSet("siblingsIds", siblingsIds)
                .addSet("siblingsNames", siblingsNames)
                .addSet("brothersNames", brothersNames)
                .addSet("sistersNames", sisterNames)
                .addSet("parentsIds", parentsIds)
                .addSet("parentsNames", parentsNames)
                .buildString();
    }

    private PersonInfo addString(String fieldName, Object object) {
        if (object != null) {
            stringBuilder.append(fieldName).append("=").append(object).append(", ");
        }
        return this;
    }

    private PersonInfo addSet(String fieldName, Set<String> set) {
        if (!set.isEmpty()) {
            stringBuilder.append(fieldName).append("=").append(set).append(", ");
        }
        return this;
    }


    private String buildString() {
        return stringBuilder.append(" }\n").toString();
    }

    private String trimToNormal(String name) {
        String[] names = name.trim().split("\\s+");
        return names[0] + " " + names[1];
    }

    //getters

    public String getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSiblingsNumber() {
        return siblingsNumber;
    }

    public Integer getChildrenNumber() {
        return childrenNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public String getWifeId() {
        return wifeId;
    }

    public String getWifeName() {
        return wifeName;
    }

    public String getHusbandId() {
        return husbandId;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public String getMotherId() {
        return motherId;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getFatherId() {
        return fatherId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public Set<String> getChildrenIds() {
        return childrenIds;
    }

    public Set<String> getChildrenNames() {
        return childrenNames;
    }

    public Set<String> getSonsIds() {
        return sonsIds;
    }

    public Set<String> getSonsNames() {
        return sonsNames;
    }

    public Set<String> getDaughtersIds() {
        return daughtersIds;
    }

    public Set<String> getDaughtersNames() {
        return daughtersNames;
    }

    public Set<String> getSiblingsIds() {
        return siblingsIds;
    }

    public Set<String> getSiblingsNames() {
        return siblingsNames;
    }

    public Set<String> getParentsIds() {
        return parentsIds;
    }

    public Set<String> getParentsNames() {
        return parentsNames;
    }

    public Set<String> getBrothersIds() {
        return brothersIds;
    }

    public Set<String> getBrothersNames() {
        return brothersNames;
    }

    public Set<String> getSisterIds() {
        return sisterIds;
    }

    public Set<String> getSisterNames() {
        return sisterNames;
    }

    public String getFullName(){
        return firstName + " " + familyName;
    }
}
