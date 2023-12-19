package ru.nsu.ablaginin;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PeopleInfoNormalizer {

    public ArrayList<PersonInfo> normalize(List<PersonInfo> data, Integer peopleCount) {
        HashMap<String, PersonInfo> id_records = new HashMap<>();
        ArrayList<PersonInfo> temp_records = new ArrayList<>();

        System.out.println("Normalizing");

        // fill up records with known id
        for (PersonInfo i : data) {
            if (i.getId() != null) {
                if (id_records.containsKey(i.getId())) {
                    id_records.get(i.getId()).merge(i);
                } else {
                    id_records.put(i.getId(), i);
                }
            } else {
                temp_records.add(i);
            }
        }

        // check that number of ids is equal to number of people
        assert id_records.size() == peopleCount;
        // check that all id records have both first and last names
        assert id_records.values().parallelStream().allMatch(x -> x.getFirstName() != null && x.getFamilyName() != null);
        assert temp_records.parallelStream().allMatch(x -> x.getFirstName() != null && x.getFamilyName() != null);

        data = temp_records;
        temp_records = new ArrayList<>();

        for (PersonInfo p : data) {
            List<PersonInfo> found = findInRecords(x -> Optional.of(x)
                            .map(PersonInfo::getFirstName)
                            .map(v -> v.equals(p.getFirstName()))
                            .orElse(false) &&
                            Optional.of(x)
                                    .map(PersonInfo::getFamilyName)
                                    .map(v -> v.equals(p.getFamilyName()))
                                    .orElse(false),
                    id_records.values());
            if (found.size() == 1) {
                PersonInfo foundPerson = found.get(0);
                foundPerson.merge(p);
                id_records.replace(foundPerson.getId(), foundPerson);
            } else if (found.size() > 1) {
                PersonInfo foundPerson = found.get(0);
                temp_records.addAll(found);
                if (foundPerson.getGender() == null && p.getGender() != null) {
                    foundPerson.merge(p);
                }
            }
        }

        data = temp_records;
        temp_records = new ArrayList<>();

        for (PersonInfo person : data) {
            if (person.getSiblingsIds() != null) {
                Set<String> siblings = person.getSiblingsIds();
                List<PersonInfo> found = findInRecords(x -> {
                    Set<String> xsib = x.getSiblingsIds();
                    xsib.retainAll(siblings);
                    return !xsib.isEmpty();
                }, id_records.values());
                if (found.size() == 1) {
                    found.get(0).merge(person);
                } else {
                    temp_records.add(person);
                }
            }
        }

        System.out.println("Normalized!!!");
        System.out.println("Temp buffer size: " + temp_records.size());

        childrenAssertion(id_records);
        siblingsAssertion(id_records);
        genderAssertion(id_records);

        return new ArrayList<>(id_records.values());
    }

    private void mergePeople(HashMap<String, PersonInfo> id_records, String name, Consumer<String> consumer) {
        List<PersonInfo> f = findInRecords(x -> name.equals(x.getFullName()), id_records.values());
        if (!f.isEmpty()) {
            PersonInfo personInfo = f.get(0);
            if (personInfo != null) {
                consumer.accept(personInfo.getId());
            }
        }
    }

    private void childrenAssertion(HashMap<String, PersonInfo> id_records) {
        System.out.println("Children assertion");
        for (String key : id_records.keySet()) {
            PersonInfo p = id_records.get(key);
            p.getChildrenIds().addAll(p.getSonsIds());
            p.getChildrenIds().addAll(p.getDaughtersIds());

            p.getDaughtersNames().forEach(name -> mergePeople(id_records, name, p::addChildId));
            p.getSonsNames().forEach(name -> mergePeople(id_records, name, p::addChildId));
            p.getChildrenNames().forEach(name -> mergePeople(id_records, name, p::addChildId));
        }
    }

    private void siblingsAssertion(HashMap<String, PersonInfo> id_records) {
        System.out.println("Siblings assertion");
        for (String key : id_records.keySet()) {
            PersonInfo p = id_records.get(key);
            p.getSiblingsIds().addAll(p.getBrothersIds());
            p.getSiblingsIds().addAll(p.getSisterIds());

            p.getSisterNames().forEach(name -> mergePeople(id_records, name, p::addSiblingId));
            p.getBrothersNames().forEach(name -> mergePeople(id_records, name, p::addSiblingId));
            p.getSiblingsNames().forEach(name -> mergePeople(id_records, name, p::addSiblingId));
        }
    }

    private void genderAssertion(HashMap<String, PersonInfo> id_records) {
        System.out.println("Gender assertion");
        for (PersonInfo p : id_records.values()) {
            if (p.getGender() == null) {
                if (p.getWifeId() != null || p.getWifeName() != null) {
                    p.setGender("male");
                } else if (p.getHusbandId() != null || p.getHusbandName() != null) {
                    p.setGender("female");
                } else if (p.getSpouseId() != null) {
                    PersonInfo pp = id_records.get(p.getSpouseId());
                    if (pp.getGender() != null) {
                        if (pp.getGender().equals("male")) {
                            p.setGender("female");
                        }
                        if (pp.getGender().equals("female")) {
                            p.setGender("male");
                        }
                    } else if (pp.getHusbandName() != null || pp.getHusbandId() != null) {
                        p.setGender("male");
                    } else if (pp.getWifeName() != null || pp.getWifeId() != null) {
                        p.setGender("female");
                    }
                } else {
                    p.setGender("male");
                }
            }
        }
    }

    private List<PersonInfo> findInRecords(Predicate<PersonInfo> pred, Collection<PersonInfo> coll) {
        return coll.parallelStream().filter(pred).collect(Collectors.toList());
    }
}
