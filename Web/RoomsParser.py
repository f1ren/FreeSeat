#!/usr/bin/env python

from bs4 import BeautifulSoup

CELLS_IN_ROW = 9

class RoomsParser(object):
    def __init__(self):
        self.fieldCounter = 0
    def feed(self, html):
        h1 = hash(html)

        # Getting the hours out of the tables
        html = html.replace("<table><tr><td>", "")
        html = html.replace("</td><td>-</td><td>", "-")
        html = html.replace("<td> </tr></table ", "")
        html = html.replace("<td> <td>", " ")

        # Close some opened trs
        html = html.replace("</td><tr>", "</td></tr>")
        html = html.replace("<tr><tr>", "</tr><tr>")
        if h1 == hash(html):
            raise "ERROR!"
        self.soup = BeautifulSoup(html)
    def _iterateTable(self, table):
        result = []
        counter = 0
        nextRoomFields = []
        for td in table.find_all('td'):
            text = td.string
            if text is None and not td.font is None:
                print len(td.font.b.string)
                text = td.font.b.string
            if text is None and not td.a is None:
                text = td.a.get("href")
            if text is None or text == "" or len(text) < 3:
                continue
            #if text is None and not td.table is None:
            #    text = td.table.get_text
            if text == "-" or \
                (text[2] == ":" and len(text) == 5):
                        continue
            if text is None:
                print td
                raise "Oh no!"
            counter += 1
            nextRoomFields.append(text)
            if counter == 5:
                result.append(nextRoomFields)
                counter = 0
                nextRoomFields = []
        return result
    def getExamsOld(self):
        result = []
        for table in self.soup.find_all('table'):
            borderAtt = table.get("border")
            cellpadAtt = table.get("cellpadding")
            if borderAtt == "1" and cellpadAtt == "3":
                result += self._iterateTable(table)
        return result
    def getRooms(self):
        result = {}
        for tr in self.soup.table.find_all("tr")[1:]:
            if len(tr) == 5 and tr.string is None:
                room = []
                td = tr.td
                while td != None:
                    text = td.string
                    if text is None:
                        if not td.a is None:
                            text = td.a.get("href")
                        else:
                            text = td.get_text()
                    if not text is None and len(text) > 1:
                        if len(room) == 0 or text.replace(" ", "") != room[-1].replace(" ", ""):
                            room.append(text)
                    td = td.next
                # Use room caption as key
                result[room[1]] = room[:5]
        return result

if __name__ == "__main__":
    """
    ROOMS_URL = r"http://gezer.bgu.ac.il/compclass/compclass.php"
    import urllib2
    print "opening url"
    req = urllib2.urlopen(ROOMS_URL)
    print "read html"
    remote = req.read()
    print "html was read"
    """
    rp = RoomsParser()
    local = open("compclass.htm").read()
    rp.feed(local)
    r = rp.getRooms()
    """
    of = open("comclassout.htm", "wb")
    of.write(remote)
    """
    #print r
    print "LEN = %d" % len(r)
    for i in r:
        print i
        #of.write(i.__repr__())
        #for f in i:
        #    print f
            #f = unicode(f.strip(codecs.BOM_UTF8), 'uft-8')
            #of.write(f)
        #of.write("\n")
