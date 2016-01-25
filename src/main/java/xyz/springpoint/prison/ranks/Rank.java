/*
 * Copyright (C) 2016  Springpoint Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.springpoint.prison.ranks;

import java.util.List;

/**
 * A data structure that contains the information for ranks.
 *
 * @author SirFaizdat
 */
public class Rank {

    // =======================
    //  Fields
    // =======================

    public String name;
    public String tag;
    public double price, xp;
    public List<String> commands; // Commands to run on rankup

}
