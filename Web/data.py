#!/usr/bin/env python

from google.appengine.ext import db

class Room(db.Model):
    """Models a student with user name, ID and a password"""
    longName = db.StringProperty()
    sections = db.StringProperty()
    building = db.StringProperty()
    name = db.StringProperty()
    occupied = db.IntegerProperty()
    free = db.IntegerProperty()
    total= db.IntegerProperty()
    link = db.StringProperty()
    rate = db.IntegerProperty()

    def color(self):
        if self.total == 0:
            greenValue = 0
        else:
            greenValue = 190 * self.free/self.total
        return "#00%s00" % hex(greenValue)[2:4]
